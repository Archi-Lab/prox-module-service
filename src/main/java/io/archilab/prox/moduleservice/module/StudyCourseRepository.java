package io.archilab.prox.moduleservice.module;

import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface StudyCourseRepository extends PagingAndSortingRepository<StudyCourse, UUID> {

  List<StudyCourse> findByAcademicDegree(AcademicDegree academicDegree);

}
