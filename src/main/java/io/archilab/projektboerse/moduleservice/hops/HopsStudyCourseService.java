//package io.archilab.projektboerse.moduleservice.hops;
//
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import io.archilab.projektboerse.moduleservice.studycourse.AcademicDegree;
//import io.archilab.projektboerse.moduleservice.studycourse.StudyCourse;
//import io.archilab.projektboerse.moduleservice.studycourse.StudyCourseName;
//import io.archilab.projektboerse.moduleservice.studycourse.StudyCourseRepository;
//import java.io.InputStream;
//import java.util.List;
//import java.util.Optional;
//import javax.transaction.Transactional;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//@Service
//@Transactional
//@Slf4j
//public class HopsStudyCourseService {
//
//  private final HopsClient hopsClient;
//
//  private final StudyCourseRepository studyCourseRepository;
//
//  private final HopsStudyCourseMappingRepository hopsStudyCourseMappingRepository;
//
//  private final ObjectMapper objectMapper;
//
//  public HopsStudyCourseService(HopsClient hopsClient, StudyCourseRepository studyCourseRepository,
//      HopsStudyCourseMappingRepository hopsStudyCourseMappingRepository,
//      ObjectMapper objectMapper) {
//    this.hopsClient = hopsClient;
//    this.studyCourseRepository = studyCourseRepository;
//    this.hopsStudyCourseMappingRepository = hopsStudyCourseMappingRepository;
//    this.objectMapper = objectMapper;
//  }
//
//  public void importStudyCourses() {
//
//    // Load study courses from HoPS API
//    List<HopsStudyCourse> hopsStudyCourses;
//
//    // If HoPS-Client throws exception due to problems with the HoPS-API or if the API returns
//    // "null" in the response body (which it does unfortunately in some cases) do not try to import
//    // data from HoPS.
//    try {
//      hopsStudyCourses = this.hopsClient.getHopsStudyCourses();
//    } catch (Exception e) {
//      hopsStudyCourses = null;
//    }
//    // Unfortunately the HoPS API just returns "null" in the response body if its backend is down
//    // and does not reply with a corresponding error code or message and not even valid JSON so the
//    // HopsClient just returns null instead of a list.
//    if (hopsStudyCourses == null) {
//      HopsStudyCourseService.log.info("Study courses HoPS-API is not working properly!");
//
//      TypeReference<List<HopsStudyCourse>> typeReference = new TypeReference<List<HopsStudyCourse>>() {
//      };
//      InputStream inputStream = TypeReference.class
//          .getResourceAsStream("/sample-data/studycourses.json");
//      try {
//        hopsStudyCourses = this.objectMapper.readValue(inputStream, typeReference);
//        this.importFromHops(hopsStudyCourses);
//        HopsStudyCourseService.log.info("Study courses sample data import succeeded");
//      } catch (Exception e) {
//        HopsStudyCourseService.log.info("Study courses sample data import failed");
//        throw new RuntimeException(e);
//      }
//    } else {
//      this.importFromHops(hopsStudyCourses);
//    }
//  }
//
//  private void importFromHops(List<HopsStudyCourse> hopsStudyCourses) {
//    for (HopsStudyCourse hopsStudyCourse : hopsStudyCourses) {
//
//      // Parse relevant data and convert to domain model
//      HopsStudyCourseId hopsId = new HopsStudyCourseId(hopsStudyCourse.getBezeichnung());
//      String[] tokens = hopsStudyCourse.getBezeichnung().split(": ");
//      AcademicDegree academicDegree =
//          tokens[0].equals("Master") ? AcademicDegree.MASTER : AcademicDegree.BACHELOR;
//      StudyCourseName studyCourseName = new StudyCourseName(tokens[1]);
//
//      // Update existing study course with new data or create a new one from scratch
//      StudyCourse studyCourse;
//      Optional<HopsStudyCourseMapping> studyCourseMapping = this.hopsStudyCourseMappingRepository
//          .findByHopsId(hopsId);
//      if (studyCourseMapping.isPresent()) {
//        HopsStudyCourseService.log
//            .debug("Study course with HoPS ID " + hopsId + " already exists.");
//        studyCourse = this.studyCourseRepository
//            .findById(studyCourseMapping.get().getStudyCourseId())
//            .get();
//        studyCourse.setName(studyCourseName);
//        studyCourse.setAcademicDegree(academicDegree);
//        studyCourse = this.studyCourseRepository.save(studyCourse);
//      } else {
//        HopsStudyCourseService.log
//            .debug("Study course with HoPS ID " + hopsId + " does not exist yet.");
//        studyCourse = new StudyCourse(studyCourseName, academicDegree);
//        studyCourse = this.studyCourseRepository.save(studyCourse);
//        this.hopsStudyCourseMappingRepository
//            .save(new HopsStudyCourseMapping(hopsId, studyCourse.getId()));
//      }
//
//      HopsStudyCourseService.log.debug("Imported " + hopsStudyCourse + "into " + studyCourse);
//    }
//  }
//
//}
