package io.archilab.prox.moduleservice.config;

import io.archilab.prox.moduleservice.hops.HopsImportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class ImportConfig {

  @Bean
  CommandLineRunner runImport(HopsImportService hopsImportService) {
    return args -> hopsImportService.importData();
  }

}
