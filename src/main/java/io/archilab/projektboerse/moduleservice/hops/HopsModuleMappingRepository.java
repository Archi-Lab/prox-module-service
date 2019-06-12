package io.archilab.projektboerse.moduleservice.hops;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface HopsModuleMappingRepository extends
    PagingAndSortingRepository<HopsModuleMapping, UUID> {

  List<HopsModuleMapping> findByHopsId(HopsModuleId hopsId);
  
  Optional<HopsModuleMapping> findByModuleId(UUID moduleId);

}
