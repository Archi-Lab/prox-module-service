package io.archilab.prox.moduleservice.hops;

import io.archilab.prox.moduleservice.studycourse.AcademicDegree;
import io.archilab.prox.moduleservice.studycourse.Module;
import io.archilab.prox.moduleservice.studycourse.ModuleDescription;
import io.archilab.prox.moduleservice.studycourse.ModuleName;
import io.archilab.prox.moduleservice.studycourse.ModuleRepository;
import io.archilab.prox.moduleservice.studycourse.StudyCourse;
import io.archilab.prox.moduleservice.studycourse.StudyCourseName;
import io.archilab.prox.moduleservice.studycourse.StudyCourseRepository;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class StartupLoadingService {

  @Autowired
  private HopsModuleMappingRepository hopsModuleMappingRepository;

  @Autowired
  private HopsStudyCourseMappingRepository hopsStudyCourseMappingRepository;

  @Autowired
  private StudyCourseRepository studyCourseRepository;

  @Autowired
  private ModuleRepository moduleRepository;

  public void updateData(ArrayList<HopsModule> hopsModuleGET,
      ArrayList<HopsStudyCourse> hopsStudyCourseGET,
      ArrayList<ModStuMappingHOPS> mappingHopsGET) {

    // doppleungune entfernen
    for (int i = 0; i < hopsModuleGET.size(); i++) {
      HopsModule module = hopsModuleGET.get(i);
      String inputDate = module.getDATEVERSION();
      SimpleDateFormat parser = new SimpleDateFormat("dd.mm.yy");
      Date date_active = null;
      try {
        date_active = parser.parse(inputDate);
      } catch (ParseException e) {
        StartupLoadingService.log.info("Failed to parse Date");

        e.printStackTrace();
      }
      boolean isOld = false;
      for (int k = 0; k < hopsModuleGET.size(); k++) {
        HopsModule tempModule = hopsModuleGET.get(k);
        Date date_other = null;
        try {
          date_other = parser.parse(tempModule.getDATEVERSION());
        } catch (ParseException e) {
          StartupLoadingService.log.info("Failed to parse Date");

          e.printStackTrace();
        }

        if (module.getMODULKUERZEL().equals(tempModule.getMODULKUERZEL())
            && date_active.compareTo(date_other) < 0) {
          isOld = true;
          break;
        }
      }
      if (isOld) {
        hopsModuleGET.remove(i);
        i--;
      }
    }

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

    hopsModuleGET.removeIf(hopsModule -> hopsModule.getMODULKUERZEL().equals("1384"));

    mappingHopsGET
        .removeIf(modStuMappingHOPS -> modStuMappingHOPS.getMODULKUERZEL().equals("1384"));

    // doppelungen entfernen ModStuMappingHOPS aber nur vlt. weil dort nur ide kürzel von intersse
    // sind, nciht die weiteren daten. und der primary key unklar ist.
    // wenn sich die kürzel nicht ändern können, macht es keinen sinn.

    // study courses vorbereiten
    for (HopsStudyCourse studyCourse : hopsStudyCourseGET) {
      String kürzel = studyCourse.getSG_KZ();
      Optional<HopsStudyCourseMapping> scMapping =
          this.hopsStudyCourseMappingRepository.findByHopsId(new HopsStudyCourseId(kürzel));
      AcademicDegree academicDegree = null;
      StudyCourse newSC = null;

      if (studyCourse.getABSCHLUSSART().equals("Master")) {
        academicDegree = AcademicDegree.MASTER;
      } else if (studyCourse.getABSCHLUSSART().equals("Bachelor")) {
        academicDegree = AcademicDegree.BACHELOR;
      } else {
        StartupLoadingService.log.info(studyCourse.getABSCHLUSSART());
        academicDegree = AcademicDegree.UNKNOWN;
      }

      if (!scMapping.isPresent()) {
        // prepare to add new study Course and save the mapping
        newSC = new StudyCourse(new StudyCourseName(studyCourse.getSTUDIENGANG()), academicDegree);

        HopsStudyCourseMapping newStudyCourseMapper =
            new HopsStudyCourseMapping(new HopsStudyCourseId(kürzel), newSC.getId());
        this.hopsStudyCourseMappingRepository.save(newStudyCourseMapper);

      } else {
        // get study Course from database
        Optional<StudyCourse> optSC =
            this.studyCourseRepository.findById(scMapping.get().getStudyCourseId());
        newSC = optSC.get();

      }

      // fill study course or update
      newSC.setAcademicDegree(academicDegree);
      newSC.setName(new StudyCourseName(studyCourse.getSTUDIENGANG()));

      newSC = this.studyCourseRepository.save(newSC);
    }

    // module einarbeiten
    StartupLoadingService.log.info("module einarbeiten");

    for (HopsModule module : hopsModuleGET) {

      // Potentieller Filter
      // String bezeichnung = module.getMODULBEZEICHNUNG();
      //
      // if( !( bezeichnung.equals("Master Thesis (English)") || bezeichnung.equals("Master Thesis
      // and Colloquium (English)") ||
      // bezeichnung.equals("Masterarbeit") || bezeichnung.equals("Masterarbeit und Kolloquium
      // (German)")
      // || bezeichnung.equals("Bachelorarbeit") || bezeichnung.equals("Kolloquium zur
      // Bachelorarbeit")
      // || bezeichnung.equals("Bachelor Kolloquium") || bezeichnung.equals("Bachelor Arbeit ")
      // || bezeichnung.equals("Bachelor Arbeit ") Praxisprojekt Masterarbeit und Kolloquium ) )
      // {
      // continue;
      // }

      ArrayList<ModStuMappingHOPS> doppelt = new ArrayList<>();

      String kürzel = module.getMODULKUERZEL();
      for (ModStuMappingHOPS mapping : mappingHopsGET) {
        // finde das modul
        if (mapping.getMODULKUERZEL().equals(kürzel)) {
          doppelt.add(mapping);
        }
      }

      Iterable<StudyCourse> iterable = this.studyCourseRepository.findAll();

      List<StudyCourse> studyCourses = new ArrayList<>();
      iterable.forEach(studyCourses::add);

      Module newModule = null;

      List<HopsModuleMapping> moMapping = this.hopsModuleMappingRepository
          .findByHopsId(new HopsModuleId(kürzel, module.getDATEVERSION()));

      // erstmal update existierende module

      for (HopsModuleMapping moMapped : moMapping) {
        Optional<Module> optMO = this.moduleRepository.findById(moMapped.getModuleId());
        newModule = optMO.get();
        this.fillModule(newModule, module);

        this.moduleRepository.save(newModule);
      }

      // regel: jedes Modul hat max. 1 studiengang. Daher werden gewisse Hops Module geklont.
      for (ModStuMappingHOPS doppelEle : doppelt) {

        // teste, ob das element bereits existiert
        Optional<HopsStudyCourseMapping> hopsScMap = this.hopsStudyCourseMappingRepository
            .findByHopsId(new HopsStudyCourseId(doppelEle.getSG_KZ()));
        if (hopsScMap.isPresent()) {

          Optional<StudyCourse> optSc =
              this.studyCourseRepository.findById(hopsScMap.get().getStudyCourseId());
          if (optSc.isPresent()) {
            // wenn nein, kreire ein neues modul und speichere es ab
            boolean moduleMissing = true;
            StudyCourse tempSc = optSc.get();

            for (Module tempModule : tempSc.getModules()) {
              Optional<HopsModuleMapping> tempModuleMapping =
                  this.hopsModuleMappingRepository.findByModuleId(tempModule.getId());
              if (tempModuleMapping.isPresent()) {
                if (tempModuleMapping.get().getHopsId().getKuerzel()
                    .equals(module.getMODULKUERZEL())) {
                  moduleMissing = false;
                  break;
                }
              } else {
                StartupLoadingService.log.info("missing map");
              }
            }

            if (moduleMissing == true) {
              newModule = this.createAndFillModule(module);
              newModule = this.moduleRepository.save(newModule);

              tempSc.addModule(newModule);
              this.hopsModuleMappingRepository.save(new HopsModuleMapping(
                  new HopsModuleId(module.getMODULKUERZEL(), module.getDATEVERSION()),
                  newModule.getId()));
              // verlinke es mit studiengang
              tempSc = this.studyCourseRepository.save(tempSc);
            }
          } else {
            StartupLoadingService.log.info("partly Error  study Course should not be missing ");
          }
        } else {
          StartupLoadingService.log.info(
              "partly Error  study Course mapping should not be missing, maybe Module not in use "
                  + doppelEle.getSG_KZ() + " " + doppelEle.getID());
        }

      }
    }

    // Status Info über den Import
    StartupLoadingService.log.info("Status Info");
    {
      long size =
          StreamSupport.stream(this.moduleRepository.findAll().spliterator(), false).count();
      StartupLoadingService.log.info("Anzahl Module: " + String.valueOf(size));
    }

    {
      long size =
          StreamSupport.stream(this.studyCourseRepository.findAll().spliterator(), false).count();
      StartupLoadingService.log.info("Anzahl Studiengänge: " + String.valueOf(size));
    }
    StartupLoadingService.log.info("Alle Studiengänge und Anzahl verlinkter Module");
    {
      for (StudyCourse sc : this.studyCourseRepository.findAll()) {
        StartupLoadingService.log.info("Name:   " + sc.getName());
        StartupLoadingService.log.info("Module: " + String.valueOf(sc.getModules().size()));
      }
    }

    StartupLoadingService.log.info("All Done. Start normal operation.");

  }

  // Helper Funktion: erstellt neues Modul und füllt es mit daten aus dem Hops Modul
  private Module createAndFillModule(HopsModule module) {
    Module newM = new Module(new ModuleName(""), new ModuleDescription(""));
    this.fillModule(newM, module);
    return newM;
  }


  // Helper Funktion: füllt ein Modul mit daten aus dem Hops Modul
  private void fillModule(Module newModule, HopsModule module) {
    newModule.setName(new ModuleName(module.getMODULBEZEICHNUNG()));
    newModule.setDescription(
        new ModuleDescription((module.getINHALT() != null ? module.getINHALT() : "")));
  }


}
