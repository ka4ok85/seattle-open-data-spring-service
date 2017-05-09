package com.example.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.example.entity.Call;

@RepositoryRestResource
public interface CallRepository extends JpaRepository<Call, Long> {

	@RestResource (path = "by-type")
	Collection<Call> findByType(@Param("type") String type);

}
