package io.archilab.prox.moduleservice.module;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface ModuleRepository extends PagingAndSortingRepository<Module, UUID> {

  Page<Module> findByName_NameContainingIgnoreCase(@Param("name") String name, Pageable pageable);

  Page<Module> findByProjectType(@Param("projectType") ProjectType projectType, Pageable pageable);

}
