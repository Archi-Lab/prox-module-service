package io.archilab.prox.moduleservice.module;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import io.archilab.prox.moduleservice.core.AbstractEntity;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyCourse extends AbstractEntity {

  @Setter @JsonUnwrapped private StudyCourseName name;

  @Setter private AcademicDegree academicDegree;

  @OneToMany(
      cascade = {CascadeType.PERSIST, CascadeType.MERGE},
      fetch = FetchType.LAZY)
  @JoinColumn(name = "module_id")
  private Set<Module> modules = new HashSet<>();

  public StudyCourse(StudyCourseName name, AcademicDegree academicDegree) {
    this.name = name;
    this.academicDegree = academicDegree;
  }

  public Set<Module> getModules() {
    return Collections.unmodifiableSet(this.modules);
  }

  public void addModule(Module module) {
    this.modules.add(module);
  }

  public void removeModule(Module module) {
    this.modules.remove(module);
  }

  public void removeAllModules() {
    this.modules = new HashSet<>();
  }
}
