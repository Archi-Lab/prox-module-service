package io.archilab.projektboerse.moduleservice.studycourse;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.archilab.projektboerse.moduleservice.ModuleService;

@RunWith(SpringRunner.class)
@DataJpaTest
public class StudyCourseTests {

  @Autowired
  StudyCourseRepository repository;

  @Test
  public void equality() {
    StudyCourseName name = new StudyCourseName("name");
    AcademicDegree bachelor = AcademicDegree.BACHELOR;
    StudyCourse studyCourse1 = new StudyCourse(name, bachelor);
    StudyCourse studyCourse2 = new StudyCourse(name, bachelor);
    assertThat(studyCourse1.getId()).isNotEqualTo(studyCourse2.getId());
    assertThat(studyCourse1).isNotEqualTo(studyCourse2);
    assertThat(studyCourse1.hashCode()).isNotEqualTo(studyCourse2.hashCode());
    this.repository.save(studyCourse1);
    StudyCourse studyCourse3 = this.repository.findById(studyCourse1.getId()).get();
    assertThat(studyCourse1).isEqualTo(studyCourse3);
    assertThat(studyCourse1.hashCode()).isEqualTo(studyCourse3.hashCode());
  }

}
