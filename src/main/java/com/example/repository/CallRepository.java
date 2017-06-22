package com.example.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.example.entity.Call;

@RepositoryRestResource
public interface CallRepository extends CrudRepository<Call, Long> {

	@RestResource (path = "by-type")
	Collection<Call> findByType(@Param("type") String type);

	@Override
	@RestResource(exported = false)
	void delete(Long id);
	
	@Override
	@RestResource(exported = false)
	void delete(Call call);
	
	@Override
	@RestResource(exported = false)
	void delete(Iterable<? extends Call> entities);

	@Override
	@RestResource(exported = false)
	void deleteAll();
	

    @Override
    @RestResource(exported = false)
    public Call save(Call call);

    @Override
    @RestResource(exported = false)
    <S extends Call> List<S> save(Iterable<S> entities);
}
