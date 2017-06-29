package com.example.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.example.entity.WeatherRecord;

@RepositoryRestResource(path="weather")
public interface WeatherRecordRepository extends CrudRepository<WeatherRecord, Long> {
	@Override
	@RestResource(exported = false)
	void delete(Long id);

	@Override
	@RestResource(exported = false)
	void delete(WeatherRecord weatherRecord);

	@Override
	@RestResource(exported = false)
	void delete(Iterable<? extends WeatherRecord> entities);

	@Override
	@RestResource(exported = false)
	void deleteAll();

	@Override
	@RestResource(exported = false)
	public WeatherRecord save(WeatherRecord weatherRecord);

	@Override
	@RestResource(exported = false)
	<S extends WeatherRecord> List<S> save(Iterable<S> entities);
}
