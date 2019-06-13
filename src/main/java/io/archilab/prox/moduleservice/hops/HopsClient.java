package io.archilab.prox.moduleservice.hops;

import java.util.ArrayList;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "hops-client", url = "https://fhpwww.gm.fh-koeln.de/hops/api/project")
public interface HopsClient {

  @GetMapping("/gettables.php?table=MODULE")
  ArrayList<HopsModule> getModules();

  @GetMapping("/gettables.php?table=MSTUDIENGANGRICHTUNG")
  ArrayList<HopsStudyCourse> getStudyCourses();

  @GetMapping("/gettables.php?table=MODULECURRICULUM")
  ArrayList<HopsCurriculum> getCurricula();


}
