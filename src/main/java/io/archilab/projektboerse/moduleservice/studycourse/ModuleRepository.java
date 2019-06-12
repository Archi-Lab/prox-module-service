package io.archilab.projektboerse.moduleservice.studycourse;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;

public interface ModuleRepository extends PagingAndSortingRepository<Module, UUID> {
	
	 Page<Module> findByName_NameContainingIgnoreCase (@Param("name") String name, Pageable pageable);

}
