package io.archilab.prox.moduleservice.hops;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@Slf4j
public class CompImportInitialization {

  @Autowired
  private HopsClient hopsClient;


  @Autowired
  private StartupLoadingService startupLoadingService;

  @Bean
  public SmartInitializingSingleton importProcessor() {
    return () -> {
      this.getDatta();

      // test mehrfach import update
      // getDatta();

    };
  }

  private void getDatta() {
    CompImportInitialization.log.info("Start Data Import HOPS");

    ArrayList<HopsModule> hopsModuleGET =
        (ArrayList<HopsModule>) this.importData("MODULE", this.hopsClient::getModules);
    ArrayList<HopsStudyCourse> hopsStudyCourseGET = (ArrayList<HopsStudyCourse>) this
        .importData("MSTUDIENGANGRICHTUNG", this.hopsClient::getStudyCourses);
    ArrayList<HopsCurriculum> mappingHopsGET = (ArrayList<HopsCurriculum>) this
        .importData("MODULECURRICULUM", this.hopsClient::getCurricula);

    CompImportInitialization.log.info("Retrieved all Data from HOPS");

    CompImportInitialization.log.info("Save and Update");

    this.startupLoadingService.updateData(hopsModuleGET, hopsStudyCourseGET, mappingHopsGET);

  }

  // Hier findet der Import statt, erst wird versucht, aus dem Hops zu importieren, danach wird
  // versucht, aus den json dateien zu importieren
  private ArrayList<?> importData(String type, Supplier<ArrayList> getRequest) {
    ArrayList<?> dataToImport = null;
    try {
      CompImportInitialization.log.info("Import " + type);
      dataToImport = getRequest.get();
      if (dataToImport == null) {
        CompImportInitialization.log.info("Failed to import variable is null");
        throw new Exception("Failed to import variable is null");
      }
      CompImportInitialization.log.info("Import from HOPS API successfully done!");
    } catch (Exception e) {
      CompImportInitialization.log.info("Failed to import " + type);
      CompImportInitialization.log.info("Import " + type + " from local file");
      TypeReference<List<?>> typeReference = new TypeReference<List<?>>() {
      };
      InputStream inputStream = TypeReference.class.getResourceAsStream("/data/" + type + ".json");
      try {
        ObjectMapper objectMapper = new ObjectMapper();

        dataToImport = objectMapper.readValue(inputStream, typeReference);
        CompImportInitialization.log.info("Import from file successfully done!");
      } catch (Exception exx) {
        CompImportInitialization.log.info("Failed to import " + type + " from file");
        exx.printStackTrace();
      }
    }

    return dataToImport;
  }

}

