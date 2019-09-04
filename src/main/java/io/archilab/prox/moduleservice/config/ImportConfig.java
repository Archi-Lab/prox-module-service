package io.archilab.prox.moduleservice.config;

import io.archilab.prox.moduleservice.hops.HopsImportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
@Slf4j
@EnableScheduling
public class ImportConfig implements SchedulingConfigurer {

  @Autowired
  private Environment env;

  private boolean initialStart = true;

  @Autowired
  HopsImportService hopsImportService;

  @Bean
  public Executor taskExecutor() {
    return Executors.newScheduledThreadPool(100);
  }

  @Override
  public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
    taskRegistrar.setScheduler(taskExecutor());
    taskRegistrar.addTriggerTask(
            () -> hopsImportService.importData(),
            triggerContext -> {

              Calendar nextExecutionTime = new GregorianCalendar();

              if(initialStart){
                initialStart = false;
                return nextExecutionTime.getTime();
              }

              boolean hasData = hopsImportService.hasData();

              if (hasData) {
                ImportConfig.log.info("importData: has data");
                nextExecutionTime.add(Calendar.MINUTE, Integer.valueOf(env.getProperty("importHops.delay.hasData.minutes")));
              } else {
                ImportConfig.log.info("importData: has no data");
                nextExecutionTime.add(Calendar.SECOND, Integer.valueOf(env.getProperty("importHops.delay.hasNoData.seconds")));
              }

              return nextExecutionTime.getTime();
            }
    );
  }
}
