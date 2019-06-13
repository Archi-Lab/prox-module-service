package io.archilab.prox.moduleservice.hops;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@Slf4j
public class ImportRunner {

  private final HopsClient hopsClient;

  private final HopsImportService hopsImportService;

  public ImportRunner(HopsClient hopsClient, HopsImportService hopsImportService) {
    this.hopsClient = hopsClient;
    this.hopsImportService = hopsImportService;
  }

  @Bean
  CommandLineRunner runFeign() {
    return args -> this.getData();

  }

  private void getData() {
    ImportRunner.log.info("Start Data Import HOPS");

    ArrayList<HopsModule> hopsModules =
        (ArrayList<HopsModule>) this.importData("MODULE", this.hopsClient::getModules);
    ArrayList<HopsStudyCourse> hopsStudyCourses = (ArrayList<HopsStudyCourse>) this
        .importData("MSTUDIENGANGRICHTUNG", this.hopsClient::getStudyCourses);
    ArrayList<HopsCurriculum> hopsCurricula = (ArrayList<HopsCurriculum>) this
        .importData("MODULECURRICULUM", this.hopsClient::getCurricula);

    ImportRunner.log.info("Retrieved all Data from HOPS");

    ImportRunner.log.info("Save and Update");

    this.hopsImportService.updateData(hopsModules, hopsStudyCourses, hopsCurricula);
  }

  private ArrayList<?> importData(String type, Supplier<ArrayList> supplier) {
    ArrayList<?> dataToImport = null;
    try {
      ImportRunner.log.info("Import " + type);
      dataToImport = supplier.get();
      if (dataToImport == null) {
        ImportRunner.log.info("Failed to import variable is null");
        throw new Exception("Failed to import variable is null");
      }
      ImportRunner.log.info("Import from HOPS API successfully done!");
    } catch (Exception e1) {
      ImportRunner.log.info("Failed to import " + type);
      ImportRunner.log.info("Import " + type + " from local file");
      TypeReference<List<?>> typeReference = new TypeReference<List<?>>() {};
      InputStream inputStream = TypeReference.class.getResourceAsStream("/data/" + type + ".json");
      try {
        ObjectMapper objectMapper = new ObjectMapper();

        dataToImport = objectMapper.readValue(inputStream, typeReference);
        ImportRunner.log.info("Import from file successfully done!");
      } catch (Exception e2) {
        ImportRunner.log.info("Failed to import " + type + " from file");
        e2.printStackTrace();
      }
    }

    return dataToImport;
  }

}
