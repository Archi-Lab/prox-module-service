package io.archilab.prox.moduleservice.studycourse;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class ModuleNameTests {

  @Test
  public void equality() {
    String name = "name";
    ModuleName moduleName1 = new ModuleName(name);
    ModuleName moduleName2 = new ModuleName(name);
    assertThat(moduleName1).isEqualTo(moduleName2);
    assertThat(moduleName1.hashCode()).isEqualTo(moduleName2.hashCode());
  }

}
