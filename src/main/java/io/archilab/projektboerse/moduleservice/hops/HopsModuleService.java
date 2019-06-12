//package io.archilab.projektboerse.moduleservice.hops;
//
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import io.archilab.projektboerse.moduleservice.studycourse.Module;
//import io.archilab.projektboerse.moduleservice.studycourse.ModuleDescription;
//import io.archilab.projektboerse.moduleservice.studycourse.ModuleName;
//import io.archilab.projektboerse.moduleservice.studycourse.ModuleRepository;
//import io.archilab.projektboerse.moduleservice.studycourse.StudyCourse;
//import io.archilab.projektboerse.moduleservice.studycourse.StudyCourseRepository;
//import java.io.InputStream;
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//import javax.transaction.Transactional;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//@Service
//@Transactional
//@Slf4j
//public class HopsModuleService {
//
//  private final HopsClient hopsClient;
//
//  private final ModuleRepository moduleRepository;
//
//  private final HopsModuleMappingRepository hopsModuleMappingRepository;
//
//  private final StudyCourseRepository studyCourseRepository;
//
//  private final HopsStudyCourseMappingRepository hopsStudyCourseMappingRepository;
//
//  private final ObjectMapper objectMapper;
//
//  public HopsModuleService(HopsClient hopsClient, ModuleRepository moduleRepository,
//      HopsModuleMappingRepository hopsModuleMappingRepository,
//      StudyCourseRepository studyCourseRepository,
//      HopsStudyCourseMappingRepository hopsStudyCourseMappingRepository,
//      ObjectMapper objectMapper) {
//    this.hopsClient = hopsClient;
//    this.moduleRepository = moduleRepository;
//    this.hopsModuleMappingRepository = hopsModuleMappingRepository;
//    this.studyCourseRepository = studyCourseRepository;
//    this.hopsStudyCourseMappingRepository = hopsStudyCourseMappingRepository;
//    this.objectMapper = objectMapper;
//  }
//
//  public void importModules() {
//    // Load modules from HoPS API
//    List<HopsModule> hopsModules;
//
//    // If HoPS-Client throws exception due to problems with the HoPS-API or if the API returns
//    // "null" in the response body (which it does unfortunately in some cases) do not try to import
//    // data from HoPS.
//    try {
//      hopsModules = this.hopsClient.getHopsModules();
//    } catch (Exception e) {
//      hopsModules = null;
//    }
//    // Unfortunately the HoPS API just returns "null" in the response body if its backend is down
//    // and does not reply with a corresponding error code or message and not even valid JSON so the
//    // HopsClient just returns null instead of a list.
//    if (hopsModules == null) {
//      HopsModuleService.log.info("Modules HoPS-API is not working properly!");
//
//      TypeReference<List<HopsModule>> typeReference = new TypeReference<List<HopsModule>>() {
//      };
//      InputStream inputStream = TypeReference.class
//          .getResourceAsStream("/sample-data/modules.json");
//      try {
//        hopsModules = this.objectMapper.readValue(inputStream, typeReference);
//        this.importFromHops(hopsModules);
//        HopsModuleService.log.info("Modules sample data import succeeded!");
//      } catch (Exception e) {
//        HopsModuleService.log.info("Modules sample data import failed!");
//        throw new RuntimeException(e);
//      }
//    } else {
//      this.importFromHops(hopsModules);
//    }
//  }
//
//  private void importFromHops(List<HopsModule> hopsModules) {
//    for (HopsModule hopsModule : hopsModules) {
//
//      // Find study course by searching for its ID in the mapping repository
//      HopsStudyCourseId hopsStudyCourseId = new HopsStudyCourseId(hopsModule.getStudiengang());
//      UUID studyCourseId = this.hopsStudyCourseMappingRepository.findByHopsId(hopsStudyCourseId)
//          .get()
//          .getStudyCourseId();
//      StudyCourse studyCourse = this.studyCourseRepository.findById(studyCourseId).get();
//
//      // Parse relevant data and convert to domain model
//      HopsModuleId hopsModuleId = new HopsModuleId(
//          hopsModule.getKuerzel() == null ? hopsModule.getBezeichnung() : hopsModule.getKuerzel(),
//          hopsModule.getVersion());
//      ModuleName moduleName = new ModuleName(hopsModule.getBezeichnung());
//
//      // Update existing module with new data or create a new one from scratch
//      Module module;
//      Optional<HopsModuleMapping> moduleMapping = this.hopsModuleMappingRepository
//          .findByHopsId(hopsModuleId);
//      if (moduleMapping.isPresent()) {
//        HopsModuleService.log.debug("Module with HoPS ID " + hopsModuleId + " already exists.");
//        module = this.moduleRepository.findById(moduleMapping.get().getModuleId()).get();
//        module.setName(moduleName);
//        module = this.moduleRepository.save(module);
//      } else {
//        HopsModuleService.log.debug("Module with HoPS ID " + hopsModuleId + " does not exist yet.");
//        module = new Module(moduleName, new ModuleDescription(""));
//        module = this.moduleRepository.save(module);
//        this.hopsModuleMappingRepository.save(new HopsModuleMapping(hopsModuleId, module.getId()));
//      }
//
//      studyCourse.addModule(module);
//      this.studyCourseRepository.save(studyCourse);
//
//      HopsModuleService.log.debug("Imported " + hopsModule + "into " + module);
//    }
//  }
//
////  private void importSampleData(List<HopsModule> hopsModules) {
////    for (HopsModule hopsModule : hopsModules) {
////
////      // Find study course by searching for its ID in the mapping repository
////      HopsStudyCourseId hopsStudyCourseId = new HopsStudyCourseId(hopsModule.getStudiengang());
////      UUID studyCourseId = this.hopsStudyCourseMappingRepository.findByHopsId(hopsStudyCourseId)
////          .get()
////          .getStudyCourseId();
////      StudyCourse studyCourse = this.studyCourseRepository.findById(studyCourseId).get();
////
////      // Parse relevant data and convert to domain model
////      HopsModuleId hopsModuleId = new HopsModuleId(hopsModule.getBezeichnung(),
////          hopsModule.getVersion());
////      ModuleName moduleName = new ModuleName(hopsModule.getBezeichnung());
////
////      // Update existing module with new data or create a new one from scratch
////      Module module;
////      Optional<HopsModuleMapping> moduleMapping = this.hopsModuleMappingRepository
////          .findByHopsId(hopsModuleId);
////      if (moduleMapping.isPresent()) {
////        HopsModuleService.log.debug("Module with HoPS ID " + hopsModuleId + " already exists.");
////        HopsModuleService.log.info(moduleMapping.get().getModuleId().toString());
////        module = this.moduleRepository.findById(moduleMapping.get().getModuleId()).get();
////        module.setName(moduleName);
////        module = this.moduleRepository.save(module);
////      } else {
////        HopsModuleService.log.debug("Module with HoPS ID " + hopsModuleId + " does not exist yet.");
////        module = new Module(moduleName, new ModuleDescription(""));
////        module = this.moduleRepository.save(module);
////        this.hopsModuleMappingRepository.save(new HopsModuleMapping(hopsModuleId, module.getId()));
////      }
////
////      studyCourse.addModule(module);
////      this.studyCourseRepository.save(studyCourse);
////
////      HopsModuleService.log.debug("Imported " + hopsModule + "into " + module);
////    }
////  }
//
//}
