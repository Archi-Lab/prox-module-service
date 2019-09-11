package io.archilab.prox.moduleservice.module;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import io.archilab.prox.moduleservice.core.AbstractEntity;
import javax.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Module extends AbstractEntity {

  @Setter
  @JsonUnwrapped
  private ModuleName name;
  //
  // @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "modules")
  // private Set<StudyCourse> studyCourses = new HashSet<>();
  //

  @Setter
  @JsonUnwrapped
  private ModuleDescription description;

  // This attribute is called projectType and not moduleType because project types such as PP, BA,
  // MA, GP should not be mixed with other moduleTypes such as lextures or courses
  @Setter
  private ProjectType projectType;


  public Module(ModuleName name, ModuleDescription description) {
    this.name = name;
    this.description = description;
    this.projectType = ProjectType.UNDEFINED;
  }

  public Module(ModuleName name, ModuleDescription description, ProjectType projectType) {
    this.name = name;
    this.description = description;
    this.projectType = projectType;
  }
}
