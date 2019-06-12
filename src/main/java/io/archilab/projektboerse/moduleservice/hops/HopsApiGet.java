package io.archilab.projektboerse.moduleservice.hops;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "HopsApiGet", url = "https://fhpwww.gm.fh-koeln.de/hops/api/project")
public interface HopsApiGet {
	
	// tabelle für module
	@GetMapping("/gettables.php?table=MODULE")
	ArrayList<ModuleHOPS> getModules();
	
	// tabelle für studiengang und  vertiefung
	@GetMapping("/gettables.php?table=MSTUDIENGANGRICHTUNG")
	ArrayList<StudiengängeHOPS> getStudiengänge();
	
	// zwischentabelle mapping module studiengang
	@GetMapping("/gettables.php?table=MODULECURRICULUM")
	ArrayList<ModStuMappingHOPS> getModuleCuriculum();


	

}
