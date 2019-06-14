package io.archilab.prox.moduleservice.module;

import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Data
@Setter(AccessLevel.NONE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyCourseName {

  private static final int MAX_LENGTH = 255;

  private String name;

  public StudyCourseName(String name) {
    if (!StudyCourseName.isValid(name)) {
      throw new IllegalArgumentException(
          String.format("Name %s exceeded maximum number of %d allowed characters", name,
              StudyCourseName.MAX_LENGTH));
    }
    this.name = name;
  }

  public static boolean isValid(String name) {
    return name != null && name.length() <= StudyCourseName.MAX_LENGTH;
  }

}
