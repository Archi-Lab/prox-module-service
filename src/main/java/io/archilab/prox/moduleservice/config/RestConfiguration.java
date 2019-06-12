package io.archilab.prox.moduleservice.config;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class RestConfiguration implements RepositoryRestConfigurer {

  @Autowired
  private EntityManager entityManager;

  @Override
  public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
    config.exposeIdsFor(this.entityManager.getMetamodel().getEntities().stream()
        .map(Type::getJavaType).toArray(Class[]::new));
  }


}
