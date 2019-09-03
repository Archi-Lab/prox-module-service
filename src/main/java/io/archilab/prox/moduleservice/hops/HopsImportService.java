package io.archilab.prox.moduleservice.hops;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.archilab.prox.moduleservice.module.AcademicDegree;
import io.archilab.prox.moduleservice.module.Module;
import io.archilab.prox.moduleservice.module.ModuleDescription;
import io.archilab.prox.moduleservice.module.ModuleName;
import io.archilab.prox.moduleservice.module.ModuleRepository;
import io.archilab.prox.moduleservice.module.ProjectType;
import io.archilab.prox.moduleservice.module.StudyCourse;
import io.archilab.prox.moduleservice.module.StudyCourseName;
import io.archilab.prox.moduleservice.module.StudyCourseRepository;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.StreamSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class HopsImportService {

  private static final Map<String[], ProjectType> projectTypeModuleNames = createProjectTypeModuleNamesMap();

  private final HopsClient hopsClient;

  private final HopsModuleMappingRepository hopsModuleMappingRepository;

  private final HopsStudyCourseMappingRepository hopsStudyCourseMappingRepository;

  private final StudyCourseRepository studyCourseRepository;

  private final ModuleRepository moduleRepository;

  private final ObjectMapper objectMapper;


  public HopsImportService(HopsClient hopsClient,
      HopsModuleMappingRepository hopsModuleMappingRepository,
      HopsStudyCourseMappingRepository hopsStudyCourseMappingRepository,
      StudyCourseRepository studyCourseRepository, ModuleRepository moduleRepository,
      ObjectMapper objectMapper) {
    this.hopsClient = hopsClient;
    this.hopsModuleMappingRepository = hopsModuleMappingRepository;
    this.hopsStudyCourseMappingRepository = hopsStudyCourseMappingRepository;
    this.studyCourseRepository = studyCourseRepository;
    this.moduleRepository = moduleRepository;
    this.objectMapper = objectMapper;
  }

  public void importData() {
    HopsImportService.log.info("Start import of HoPS data");

    ArrayList<HopsModule> hopsModules =
        (ArrayList<HopsModule>) this.fetchData("MODULE", this.hopsClient::getModules);
    ArrayList<HopsStudyCourse> hopsStudyCourses = (ArrayList<HopsStudyCourse>) this
        .fetchData("MSTUDIENGANGRICHTUNG", this.hopsClient::getStudyCourses);
    ArrayList<HopsCurriculum> hopsCurricula = (ArrayList<HopsCurriculum>) this
        .fetchData("MODULECURRICULUM", this.hopsClient::getCurricula);

    HopsImportService.log.info("Retrieved all data from HoPS");

    HopsImportService.log.info("Update and save the imported data");

    this.updateData(hopsModules, hopsStudyCourses, hopsCurricula);
  }

  private ArrayList<?> fetchData(String type, Supplier<ArrayList> supplier) {
    HopsImportService.log.info("Import " + type + " from HoPS API");
    ArrayList<?> dataToImport = supplier.get();

    if (dataToImport == null) {
      HopsImportService.log.info("Failed to import " + type + " from HoPS API");
      HopsImportService.log.info("Import " + type + " from backup file");
      TypeReference<List<?>> typeReference = new TypeReference<List<?>>() {};
      InputStream inputStream = TypeReference.class.getResourceAsStream("/data/" + type + ".json");
      try {
        dataToImport = this.objectMapper.readValue(inputStream, typeReference);
        HopsImportService.log.info("Import of " + type + " from file was successful!");
      } catch (Exception e) {
        HopsImportService.log.error("Failed to import " + type + " from file", e);
      }
    } else {
      HopsImportService.log.info("Import " + type + " from HoPS API was successful!");
    }

    return dataToImport;
  }

  public void updateData(ArrayList<HopsModule> hopsModules,
      ArrayList<HopsStudyCourse> hopsStudyCourses, ArrayList<HopsCurriculum> hopsCurricula) {
    // for (int i = 0; i < hopsModules.size(); i++) {
    List<HopsModule> oldModules = new ArrayList<>();
    for (HopsModule currentModule : hopsModules) {
      // HopsModule currentModule = hopsModules.get(i);
      String dateversion = currentModule.getDATEVERSION();
      DateFormat dateFormat = new SimpleDateFormat("dd.mm.yy");
      Date date_active = null;
      try {
        date_active = dateFormat.parse(dateversion);
      } catch (ParseException e) {
        throw new RuntimeException("Failed to parse date", e);
      }
      boolean newerVersionAvailable = false;
      for (HopsModule otherModule : hopsModules) {
        Date date_other = null;
        try {
          date_other = dateFormat.parse(otherModule.getDATEVERSION());
        } catch (ParseException e) {
          throw new RuntimeException("Failed to parse date", e);
        }

        if (currentModule.getMODULKUERZEL().equals(otherModule.getMODULKUERZEL())
            && date_active.before(date_other)) {
          newerVersionAvailable = true;
          break;
        }
      }
      if (newerVersionAvailable) {
        oldModules.add(currentModule);
      }
    }
    hopsModules.removeAll(oldModules);

    // explizite filterung des moduls praxisprojekt aus der Modul Tabelle und aus der Mapping
    // Zwischentabelle
    // Begründung: dieses Modul gibt es 2 mal mit 2 unterhsciedlichen Kürzeln: 1384 und 2318
    // wesentlicher unterschied ist: dass das Modul 1384 in der Zwischentabelle das Attribut
    // ZUORDNUNG_CURRICULUM den Wert Praxis-/Forschungsbezug hat
    // und das Modul 2318 das Attribut ZUORDNUNG_CURRICULUM den Wert Vertiefung bzw. Spezialisierung
    // hat
    // Bezogen auf das Hops, wurde ermittelt, dass nur das Modul 2318 verwendet wird.
    // es gibt keinen anderen sinnhaften Unterschied.
    // daher muss es explizit gefiltert werden.

    hopsModules.removeIf(hopsModule -> hopsModule.getMODULKUERZEL().equals("1384"));

    hopsCurricula.removeIf(hopsCurriculum -> hopsCurriculum.getMODULKUERZEL().equals("1384"));

    // doppelungen entfernen HopsCurriculum aber nur vlt. weil dort nur ide kürzel von intersse
    // sind, nciht die weiteren daten. und der primary key unklar ist.
    // wenn sich die kürzel nicht ändern können, macht es keinen sinn.

    HopsImportService.log.info("Saving study courses");
    for (HopsStudyCourse hopsStudyCourse : hopsStudyCourses) {
      StudyCourseName studyCourseName = new StudyCourseName(hopsStudyCourse.getSTUDIENGANG());
      AcademicDegree academicDegree;
      if (hopsStudyCourse.getABSCHLUSSART().equals("Master")) {
        academicDegree = AcademicDegree.MASTER;
      } else if (hopsStudyCourse.getABSCHLUSSART().equals("Bachelor")) {
        academicDegree = AcademicDegree.BACHELOR;
      } else {
        HopsImportService.log.info(hopsStudyCourse.getABSCHLUSSART());
        academicDegree = AcademicDegree.UNKNOWN;
      }

      HopsStudyCourseId hopsStudyCourseId = new HopsStudyCourseId(hopsStudyCourse.getSG_KZ());
      Optional<HopsStudyCourseMapping> studyCourseMapping =
          this.hopsStudyCourseMappingRepository.findByHopsId(hopsStudyCourseId);
      StudyCourse studyCourse = null;
      if (!studyCourseMapping.isPresent()) {
        studyCourse = new StudyCourse(studyCourseName, academicDegree);

        HopsStudyCourseMapping newStudyCourseMapping =
            new HopsStudyCourseMapping(hopsStudyCourseId, studyCourse.getId());
        this.hopsStudyCourseMappingRepository.save(newStudyCourseMapping);
      } else {
        Optional<StudyCourse> studyCourseOptional =
            this.studyCourseRepository.findById(studyCourseMapping.get().getStudyCourseId());
        if (studyCourseOptional.isPresent()) {
          studyCourse = studyCourseOptional.get();
        } else {
          throw new RuntimeException(
              "Study course mapping was present but study course was not found");
        }
      }

      studyCourse.setAcademicDegree(academicDegree);
      studyCourse.setName(studyCourseName);

      this.studyCourseRepository.save(studyCourse);
    }

    HopsImportService.log.info("Saving modules");
    for (HopsModule module : hopsModules) {

      ArrayList<HopsCurriculum> duplicateCurricula = new ArrayList<>();

      String modulkuerzel = module.getMODULKUERZEL();
      for (HopsCurriculum curriculum : hopsCurricula) {
        if (curriculum.getMODULKUERZEL().equals(modulkuerzel)) {
          duplicateCurricula.add(curriculum);
        }
      }

      Iterable<StudyCourse> iterable = this.studyCourseRepository.findAll();

      List<StudyCourse> studyCourses = new ArrayList<>();
      iterable.forEach(studyCourses::add);

      Module newModule;

      List<HopsModuleMapping> moduleMappings = this.hopsModuleMappingRepository
          .findByHopsId(new HopsModuleId(modulkuerzel, module.getDATEVERSION()));

      for (HopsModuleMapping moduleMapping : moduleMappings) {
        Optional<Module> optionalModule =
            this.moduleRepository.findById(moduleMapping.getModuleId());
        if (optionalModule.isPresent()) {
          newModule = optionalModule.get();
        } else {
          throw new RuntimeException("Module mapping was present but module was not found");
        }
        this.fillModule(newModule, module);

        this.moduleRepository.save(newModule);
      }

      // regel: jedes Modul hat max. 1 studiengang. Daher werden gewisse Hops Module geklont.
      for (HopsCurriculum duplicateCurriculum : duplicateCurricula) {
        Optional<HopsStudyCourseMapping> studyCourseMapping = this.hopsStudyCourseMappingRepository
            .findByHopsId(new HopsStudyCourseId(duplicateCurriculum.getSG_KZ()));
        if (studyCourseMapping.isPresent()) {
          Optional<StudyCourse> optionalStudyCourse =
              this.studyCourseRepository.findById(studyCourseMapping.get().getStudyCourseId());
          if (optionalStudyCourse.isPresent()) {
            boolean moduleMissing = true;
            StudyCourse studyCourse = optionalStudyCourse.get();
            for (Module tempModule : studyCourse.getModules()) {
              Optional<HopsModuleMapping> tempModuleMapping =
                  this.hopsModuleMappingRepository.findByModuleId(tempModule.getId());
              if (tempModuleMapping.isPresent()) {
                if (tempModuleMapping.get().getHopsId().getKuerzel()
                    .equals(module.getMODULKUERZEL())) {
                  moduleMissing = false;
                  break;
                }
              } else {
                HopsImportService.log.info("Missing module mapping");
              }
            }

            if (moduleMissing == true) {
              newModule = this.createAndFillModule(module);
              newModule = this.moduleRepository.save(newModule);

              studyCourse.addModule(newModule);
              this.hopsModuleMappingRepository.save(new HopsModuleMapping(
                  new HopsModuleId(module.getMODULKUERZEL(), module.getDATEVERSION()),
                  newModule.getId()));

              this.studyCourseRepository.save(studyCourse);
            }
          } else {
            HopsImportService.log
                .info("Study course mapping was present but study course not found");
          }
        } else {
          HopsImportService.log
              .info("Study course mapping should not be missing, maybe the module is not in use "
                  + duplicateCurriculum.getSG_KZ() + " " + duplicateCurriculum.getID());
        }

      }
    }

    HopsImportService.log.info("Status information");

    long moduleCount =
        StreamSupport.stream(this.moduleRepository.findAll().spliterator(), false).count();
    HopsImportService.log.info("Number of modules: " + moduleCount);

    long studyCourseCount =
        StreamSupport.stream(this.studyCourseRepository.findAll().spliterator(), false).count();
    HopsImportService.log.info("Number of study courses: " + studyCourseCount);

    HopsImportService.log.info("All study courses and number of linked modules");

    for (StudyCourse studyCourse : this.studyCourseRepository.findAll()) {
      HopsImportService.log.info("Name:   " + studyCourse.getName());
      HopsImportService.log.info("Modules: " + studyCourse.getModules().size());
    }

    HopsImportService.log.info("Import complete!");

  }

  private Module createAndFillModule(HopsModule module) {
    Module newM = new Module(new ModuleName(""), new ModuleDescription(""), ProjectType.UNDEFINED);
    this.fillModule(newM, module);
    return newM;
  }

  private void fillModule(Module newModule, HopsModule module) {
    newModule.setName(new ModuleName(module.getMODULBEZEICHNUNG()));
    newModule.setDescription(
        new ModuleDescription((module.getINHALT() != null ? module.getINHALT() : "")));
    newModule.setProjectType(retrieveProjectTypeFromHopsModule(module));
  }

  private ProjectType retrieveProjectTypeFromHopsModule(HopsModule module) {
    for (Entry<String[], ProjectType> entry : projectTypeModuleNames.entrySet()) {
      for (String key : entry.getKey()) {
        if (module.getMODULBEZEICHNUNG().equalsIgnoreCase(key)) {
          return entry.getValue();
        }
      }
    }

    return ProjectType.UNDEFINED;
  }

  private static Map<String[], ProjectType> createProjectTypeModuleNamesMap() {
    Map<String[], ProjectType> myMap = new HashMap<String[], ProjectType>();
    myMap.put(new String[] {"Praxisprojekt"}, ProjectType.PP);
    myMap.put(new String[] {"Bachelorarbeit", "Bachelor Arbeit "}, ProjectType.BA);
    myMap.put(new String[] {"Masterarbeit", "Master Thesis (English)", "Masterarbeit und Kolloquium", "Masterarbeit und Kolloquium (German)"}, ProjectType.MA);
    return myMap;
  }
}
