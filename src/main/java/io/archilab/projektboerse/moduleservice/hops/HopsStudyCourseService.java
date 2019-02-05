package io.archilab.projektboerse.moduleservice.hops;

import io.archilab.projektboerse.moduleservice.studycourse.AcademicDegree;
import io.archilab.projektboerse.moduleservice.studycourse.StudyCourse;
import io.archilab.projektboerse.moduleservice.studycourse.StudyCourseName;
import io.archilab.projektboerse.moduleservice.studycourse.StudyCourseRepository;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Transactional
@Slf4j
public class HopsStudyCourseService {

  private final HopsClient hopsClient;

  private final StudyCourseRepository studyCourseRepository;

  private final HopsStudyCourseMappingRepository hopsStudyCourseMappingRepository;

  public HopsStudyCourseService(HopsClient hopsClient, StudyCourseRepository studyCourseRepository,
      HopsStudyCourseMappingRepository hopsStudyCourseMappingRepository) {
    this.hopsClient = hopsClient;
    this.studyCourseRepository = studyCourseRepository;
    this.hopsStudyCourseMappingRepository = hopsStudyCourseMappingRepository;
  }

  public void importStudyCourses() {

    // Load study courses from HoPS API
    List<HopsStudyCourse> hopsStudyCourses = this.hopsClient.getHopsStudyCourses();

    // Unfortunately the HoPS API just returns "null" in the response body if its backend is down
    // and does not reply with a corresponding error code or message and not even valid JSON so the
    // HopsClient just returns null instead of a list.
    if (hopsStudyCourses == null) {
      HopsStudyCourseService.log.debug("HoPS-API ist down!");
      return;
    }

    for (HopsStudyCourse hopsStudyCourse : hopsStudyCourses) {

      // Parse relevant data and convert to domain model
      HopsStudyCourseId hopsId = new HopsStudyCourseId(hopsStudyCourse.getBezeichnung());
      String[] tokens = hopsStudyCourse.getBezeichnung().split(": ");
      AcademicDegree academicDegree =
          tokens[0].equals("Master") ? AcademicDegree.MASTER : AcademicDegree.BACHELOR;
      StudyCourseName studyCourseName = new StudyCourseName(tokens[1]);

      // Update existing study course with new data or create a new one from scratch
      StudyCourse studyCourse;
      Optional<HopsStudyCourseMapping> studyCourseMapping = this.hopsStudyCourseMappingRepository
          .findByHopsId(hopsId);
      if (studyCourseMapping.isPresent()) {
        HopsStudyCourseService.log.debug("Study course with HoPS ID " + hopsId + " already exists.");
        studyCourse = this.studyCourseRepository.findById(studyCourseMapping.get().getStudyCourseId())
            .get();
        studyCourse.setName(studyCourseName);
        studyCourse.setAcademicDegree(academicDegree);
        studyCourse = this.studyCourseRepository.save(studyCourse);
      } else {
        HopsStudyCourseService.log.debug("Study course with HoPS ID " + hopsId + " does not exist yet.");
        studyCourse = new StudyCourse(studyCourseName, academicDegree);
        studyCourse = this.studyCourseRepository.save(studyCourse);
        this.hopsStudyCourseMappingRepository
            .save(new HopsStudyCourseMapping(hopsId, studyCourse.getId()));
      }

      HopsStudyCourseService.log.debug("Imported " + hopsStudyCourse + "into " + studyCourse);
    }
  }

}