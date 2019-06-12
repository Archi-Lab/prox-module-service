package io.archilab.projektboerse.moduleservice.hops;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface HopsStudyCourseMappingRepository extends
    PagingAndSortingRepository<HopsStudyCourseMapping, UUID> {

  Optional<HopsStudyCourseMapping> findByHopsId(HopsStudyCourseId hopsId);
  

  Optional<HopsStudyCourseMapping> findByStudyCourseId(UUID studyCourseId);

}
