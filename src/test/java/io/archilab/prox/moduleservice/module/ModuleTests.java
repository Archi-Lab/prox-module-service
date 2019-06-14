package io.archilab.prox.moduleservice.module;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ModuleTests {

  @Autowired
  ModuleRepository repository;

  @Test
  public void equality() {
    ModuleName name = new ModuleName("name");
    ModuleDescription description = new ModuleDescription("description");
    Module module1 = new Module(name, description);
    Module module2 = new Module(name, description);
    assertThat(module1.getId()).isNotEqualTo(module2.getId());
    assertThat(module1).isNotEqualTo(module2);
    assertThat(module1.hashCode()).isNotEqualTo(module2.hashCode());
    this.repository.save(module1);
    Module module3 = this.repository.findById(module1.getId()).get();
    assertThat(module1).isEqualTo(module3);
    assertThat(module1.hashCode()).isEqualTo(module3.hashCode());
  }

}
