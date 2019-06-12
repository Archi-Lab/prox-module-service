package io.archilab.projektboerse.moduleservice.studycourse;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Data
@Setter(AccessLevel.NONE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ModuleDescription {

  private static final int MAX_LENGTH = 9000;

  @Column(length = 9000)
  private String description;

  public ModuleDescription(String description) {
    if (!ModuleDescription.isValid(description)) {
      throw new IllegalArgumentException(String
          .format("Name %s exceeded maximum number of %d allowed characters", description,
              ModuleDescription.MAX_LENGTH));
    }
    this.description = description;
  }

  public static boolean isValid(String name) {
    return name != null && name.length() <= ModuleDescription.MAX_LENGTH;
  }

}
