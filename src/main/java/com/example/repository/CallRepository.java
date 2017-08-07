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

	/* Simple past n days queries */
	@Query(value = "select count(c), date(c.datetime) from Call c where c.datetime > :dateTime group by date(c.datetime)")
	List<?> getCountsDailySinceDatetime(@Param("dateTime") LocalDateTime dateTime);

	@Query(value = "select count(c), date(c.datetime) from Call c where c.type = :type AND c.datetime > :dateTime group by date(c.datetime)")
	List<?> getCountsByTypeDailySinceDatetime(@Param("type") String type, @Param("dateTime") LocalDateTime dateTime);

	@Query(value = "select count(c), date(c.datetime) from Call c JOIN c.weatherRecord w where w.zip = :zip AND c.datetime > :dateTime group by date(c.datetime)")
	List<?> getCountsByZipDailySinceDatetime(@Param("zip") String zip, @Param("dateTime") LocalDateTime dateTime);

	@Query(value = "select count(c), date(c.datetime) from Call c JOIN c.weatherRecord w where c.type = :type AND w.zip = :zip AND c.datetime > :dateTime group by date(c.datetime)")
	List<?> getCountsByTypeAndZipDailySinceDatetime(@Param("type") String type, @Param("zip") String zip,
			@Param("dateTime") LocalDateTime dateTime);

	@Query(value = "select count(c), c.type from Call c where c.datetime > :dateTime group by c.type")
	List<?> getCountsPerTypeSinceDatetime(@Param("dateTime") LocalDateTime dateTime);

	@Query(value = "select count(c), c.type from Call c JOIN c.weatherRecord w where w.zip = :zip AND c.datetime > :dateTime group by c.type")
	List<?> getCountsPerTypeByZipSinceDatetime(@Param("zip") String zip, @Param("dateTime") LocalDateTime dateTime);

	@Query(value = "select count(c), w.zip from Call c JOIN c.weatherRecord w where c.datetime > :dateTime group by w.zip")
	List<?> getCountsPerZipSinceDatetime(@Param("dateTime") LocalDateTime dateTime);

	@Query(value = "select count(c), w.zip from Call c JOIN c.weatherRecord w where c.type = :type AND c.datetime > :dateTime group by w.zip")
	List<?> getCountsPerZipByTypeSinceDatetime(@Param("type") String type, @Param("dateTime") LocalDateTime dateTime);

	/* Between start and end days queries */
	@Query(value = "select count(c), date(c.datetime) from Call c where c.datetime between :startDateTime and :endDateTime group by date(c.datetime)")
	List<?> getCountsDailyBetweenDatetimes(@Param("startDateTime") LocalDateTime startDateTime,
			@Param("endDateTime") LocalDateTime endDateTime);

	@Query(value = "select count(c), DATE_FORMAT(c.datetime,'%H') from Call c where c.datetime between :startDateTime and :endDateTime GROUP BY DATE_FORMAT(c.datetime,'%H')")
	List<?> getCountsHourlyBetweenDatetimes(@Param("startDateTime") LocalDateTime startDateTime,
			@Param("endDateTime") LocalDateTime endDateTime);
		
	@Query(value = "select count(c), date(c.datetime) from Call c where c.type = :type AND c.datetime between :startDateTime and :endDateTime group by date(c.datetime)")
	List<?> getCountsByTypeDailyBetweenDatetimes(@Param("type") String type,
			@Param("startDateTime") LocalDateTime startDateTime, @Param("endDateTime") LocalDateTime endDateTime);

	@Query(value = "select count(c), date(c.datetime) from Call c JOIN c.weatherRecord w where w.zip = :zip AND c.datetime between :startDateTime and :endDateTime group by date(c.datetime)")
	List<?> getCountsByZipDailyBetweenDatetimes(@Param("zip") String zip,
			@Param("startDateTime") LocalDateTime startDateTime, @Param("endDateTime") LocalDateTime endDateTime);

	@Query(value = "select count(c), date(c.datetime) from Call c JOIN c.weatherRecord w where w.zip IS NULL AND c.datetime between :startDateTime and :endDateTime group by date(c.datetime)")
	List<?> getCountsByNullZipDailyBetweenDatetimes(@Param("startDateTime") LocalDateTime startDateTime,
			@Param("endDateTime") LocalDateTime endDateTime);

	@Query(value = "select count(c), date(c.datetime) from Call c JOIN c.weatherRecord w where c.type = :type AND w.zip = :zip AND c.datetime between :startDateTime and :endDateTime group by date(c.datetime)")
	List<?> getCountsByTypeAndZipDailyBetweenDatetimes(@Param("type") String type, @Param("zip") String zip,
			@Param("startDateTime") LocalDateTime startDateTime, @Param("endDateTime") LocalDateTime endDateTime);

	@Query(value = "select count(c), date(c.datetime) from Call c JOIN c.weatherRecord w where c.type = :type AND w.zip IS NULL AND c.datetime between :startDateTime and :endDateTime group by date(c.datetime)")
	List<?> getCountsByTypeAndNullZipDailyBetweenDatetimes(@Param("type") String type,
			@Param("startDateTime") LocalDateTime startDateTime, @Param("endDateTime") LocalDateTime endDateTime);

	@Query(value = "select count(c), c.type from Call c where c.datetime between :startDateTime and :endDateTime group by c.type")
	List<?> getCountsPerTypeBetweenDatetimes(@Param("startDateTime") LocalDateTime startDateTime,
			@Param("endDateTime") LocalDateTime endDateTime);

	@Query(value = "select count(c), c.type from Call c JOIN c.weatherRecord w where w.zip = :zip AND c.datetime between :startDateTime and :endDateTime group by c.type")
	List<?> getCountsPerTypeByZipBetweenDatetimes(@Param("zip") String zip,
			@Param("startDateTime") LocalDateTime startDateTime, @Param("endDateTime") LocalDateTime endDateTime);

	@Query(value = "select count(c), c.type from Call c JOIN c.weatherRecord w where w.zip IS NULL AND c.datetime between :startDateTime and :endDateTime group by c.type")
	List<?> getCountsPerTypeByNullZipBetweenDatetimes(@Param("startDateTime") LocalDateTime startDateTime, @Param("endDateTime") LocalDateTime endDateTime);
	
	@Query(value = "select count(c), w.zip from Call c JOIN c.weatherRecord w where c.datetime between :startDateTime and :endDateTime group by w.zip")
	List<?> getCountsPerZipBetweenDatetimes(@Param("startDateTime") LocalDateTime startDateTime,
			@Param("endDateTime") LocalDateTime endDateTime);

	@Query(value = "select count(c), w.zip from Call c JOIN c.weatherRecord w where c.type = :type AND c.datetime between :startDateTime and :endDateTime group by w.zip")
	List<?> getCountsPerZipByTypeBetweenDatetimes(@Param("type") String type,
			@Param("startDateTime") LocalDateTime startDateTime, @Param("endDateTime") LocalDateTime endDateTime);

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
