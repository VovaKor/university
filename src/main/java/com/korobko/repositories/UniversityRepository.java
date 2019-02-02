package com.korobko.repositories;

import com.korobko.models.University;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "universities", path = "universities")
public interface UniversityRepository extends PagingAndSortingRepository<University, Long> {
    University findByName(@Param("name") String name);
}
