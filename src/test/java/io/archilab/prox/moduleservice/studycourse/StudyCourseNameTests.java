package io.archilab.prox.moduleservice.studycourse;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

public class StudyCourseNameTests {

  @Test
  public void equality() {
    String name = "Lorem ipsum";
    StudyCourseName studyCourseName1 = new StudyCourseName(name);
    StudyCourseName studyCourseName2 = new StudyCourseName(name);
    assertThat(studyCourseName1).isEqualTo(studyCourseName2);
    assertThat(studyCourseName1.hashCode()).isEqualTo(studyCourseName2.hashCode());
  }

}
