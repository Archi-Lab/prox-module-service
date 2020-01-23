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

  @Setter @JsonUnwrapped private ModuleName name;

  @Setter @JsonUnwrapped private ModuleDescription description;

  @Setter private ProjectType projectType;

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
