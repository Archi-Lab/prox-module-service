package io.archilab.projektboerse.moduleservice.hops;

import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Data
@Setter(AccessLevel.NONE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HopsStudyCourseId {

  private String study_course_kürzel;

  public HopsStudyCourseId(String study_course_kürzel) {
    this.study_course_kürzel = study_course_kürzel;
  }

}
