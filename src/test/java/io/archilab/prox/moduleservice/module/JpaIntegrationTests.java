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

  @Autowired
  ModuleRepository moduleRepository;

  @Autowired
  StudyCourseRepository studyCourseRepository;

  @Test
  public void creation() {
    StudyCourse computerScience =
        new StudyCourse(new StudyCourseName("Computer Science"), AcademicDegree.MASTER);
    StudyCourse softwareEngineering =
        new StudyCourse(new StudyCourseName("Software Engineering"), AcademicDegree.MASTER);
    StudyCourse informationSystems =
        new StudyCourse(new StudyCourseName("Information Systems"), AcademicDegree.MASTER);
    Module am =
        new Module(new ModuleName("Anforderungsmanagement"), new ModuleDescription("Lorem ipsum"));
    Module fae = new Module(new ModuleName("Fachspezifischer Architekturentwurf"),
        new ModuleDescription("Lorem ipsum"));
    Module bi =
        new Module(new ModuleName("Business Intelligence"), new ModuleDescription("Lorem ipsum"));
    Module eam = new Module(new ModuleName("Enterprise Architecture Management"),
        new ModuleDescription("Lorem ipsum"));


    softwareEngineering.addModule(am);
    softwareEngineering.addModule(fae);

    informationSystems.addModule(bi);
    informationSystems.addModule(eam);

    this.studyCourseRepository.save(computerScience);

//    assertThat(this.studyCourseRepository.findAll()).contains(computerScience, softwareEngineering,
//        informationSystems);
//    assertThat(this.moduleRepository.findAll()).contains(am, fae, bi, eam);

  }

}
