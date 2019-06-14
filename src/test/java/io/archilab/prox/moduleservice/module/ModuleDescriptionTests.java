package io.archilab.prox.moduleservice.module;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class ModuleDescriptionTests {

  @Test
  public void equality() {
    String description = "Lorem ipsum";
    ModuleDescription moduleDescription1 = new ModuleDescription(description);
    ModuleDescription moduleDescription2 = new ModuleDescription(description);
    assertThat(moduleDescription1).isEqualTo(moduleDescription2);
    assertThat(moduleDescription1.hashCode()).isEqualTo(moduleDescription2.hashCode());
  }

}
