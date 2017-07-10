package com.example.repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.example.entity.Call;

@RepositoryRestResource
public interface CallRepository extends CrudRepository<Call, Long> {

	@RestResource(path = "by-type")
	Collection<Call> findByType(@Param("type") String type);

	@Query(value = "select count(c), date(c.datetime) from Call c where c.datetime > :dateTime group by date(c.datetime)")
	List<?> getCountsDailySinceDatetime(@Param("dateTime") LocalDateTime dateTime);

	@Query(value = "select count(c), date(c.datetime) from Call c where c.type = :type AND c.datetime > :dateTime group by date(c.datetime)")
	List<?> getCountsByTypeDailySinceDatetime(@Param("type") String type, @Param("dateTime") LocalDateTime dateTime);

	@Query(value = "select count(c), c.type from Call c where c.datetime > :dateTime group by c.type")
	List<?> getCountsPerTypeSinceDatetime(@Param("dateTime") LocalDateTime dateTime);

	@Query(value = "select count(c), w.zip from Call c JOIN c.weatherRecord w where c.datetime > :dateTime group by w.zip")
	List<?> getCountsPerZipSinceDatetime(@Param("dateTime") LocalDateTime dateTime);
	
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
