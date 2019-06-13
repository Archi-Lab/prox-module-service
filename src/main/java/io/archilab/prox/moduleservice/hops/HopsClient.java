package io.archilab.prox.moduleservice.hops;

import java.util.ArrayList;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "HopsApiGet", url = "https://fhpwww.gm.fh-koeln.de/hops/api/project")
public interface HopsClient {

  @GetMapping("/gettables.php?table=MODULE")
  ArrayList<ModuleHOPS> getModules();

  @GetMapping("/gettables.php?table=MSTUDIENGANGRICHTUNG")
  ArrayList<HopsStudyCourse> getStudiengänge();

  @GetMapping("/gettables.php?table=MODULECURRICULUM")
  ArrayList<ModStuMappingHOPS> getModuleCuriculum();


}
