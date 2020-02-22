package io.archilab.prox.moduleservice.module;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class JpaIntegrationTests {

  private static final ModuleDescription LOREM_IPSUM_DESCRIPTION =
      new ModuleDescription("Lorem ipsum");
  @Autowired ModuleRepository moduleRepository;

  @Autowired StudyCourseRepository studyCourseRepository;

  @Test
  public void creation() {
    StudyCourse computerScience =
        new StudyCourse(new StudyCourseName("Computer Science"), AcademicDegree.MASTER);
    StudyCourse softwareEngineering =
        new StudyCourse(new StudyCourseName("Software Engineering"), AcademicDegree.MASTER);
    StudyCourse informationSystems =
        new StudyCourse(new StudyCourseName("Information Systems"), AcademicDegree.MASTER);
    Module am = new Module(new ModuleName("Anforderungsmanagement"), LOREM_IPSUM_DESCRIPTION);
    Module fae =
        new Module(new ModuleName("Fachspezifischer Architekturentwurf"), LOREM_IPSUM_DESCRIPTION);
    Module bi = new Module(new ModuleName("Business Intelligence"), LOREM_IPSUM_DESCRIPTION);
    Module eam =
        new Module(new ModuleName("Enterprise Architecture Management"), LOREM_IPSUM_DESCRIPTION);

    softwareEngineering.addModule(am);
    softwareEngineering.addModule(fae);

    informationSystems.addModule(bi);
    informationSystems.addModule(eam);

    this.studyCourseRepository.save(computerScience);
    this.studyCourseRepository.save(softwareEngineering);
    this.studyCourseRepository.save(informationSystems);

    assertThat(this.studyCourseRepository.findAll())
        .contains(computerScience, softwareEngineering, informationSystems);
    assertThat(this.moduleRepository.findAll()).contains(am, fae, bi, eam);
  }
}
