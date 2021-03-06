package com.example.rest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.exceptions.BadRequestException;
import com.example.repository.CallRepository;

@RestController
@RequestMapping("/calls/")
public class CallController {

	@Autowired
	private CallRepository callRepository;

	@RequestMapping(value = "/count/{startDate}/{endDate}", method = RequestMethod.GET, produces = "application/json")
	public List<?> getCountsBetweenDates(@PathVariable String startDate, @PathVariable String endDate,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "zip", required = false) String zip,
			@RequestParam(value = "hourly", required = false) String hourly) {

		LocalDateTime startDateTime;
		LocalDateTime endDateTime;
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			startDateTime = LocalDateTime.from(LocalDate.parse(startDate, formatter).atStartOfDay());
			endDateTime = LocalDateTime.from(LocalDate.parse(endDate, formatter).atStartOfDay().plusDays(1L));
		} catch (DateTimeParseException exception) {
			throw new BadRequestException("Start Date and End Date must follow YYYY-MM-DD format");
		}

		List<?> countsForPeriod;

		if (type != null && zip != null && zip.equals("0") && hourly != null) {
			return countsForPeriod = callRepository.getCountsByTypeAndNullZipHourlyBetweenDatetimes(type, startDateTime,
					endDateTime);
		}

		if (type != null && zip != null && zip.equals("0") && hourly == null) {
			return countsForPeriod = callRepository.getCountsByTypeAndNullZipDailyBetweenDatetimes(type, startDateTime,
					endDateTime);
		}
		
		if (type != null && zip != null && zip.equals("0") == false && hourly != null) {
			return countsForPeriod = callRepository.getCountsByTypeAndZipHourlyBetweenDatetimes(type, zip, startDateTime,
					endDateTime);
		}

		if (type != null && zip != null && zip.equals("0") == false && hourly == null) {
			return countsForPeriod = callRepository.getCountsByTypeAndZipDailyBetweenDatetimes(type, zip, startDateTime,
					endDateTime);
		}

		if (type != null && zip == null && hourly != null) {
			return countsForPeriod = callRepository.getCountsByTypeHourlyBetweenDatetimes(type, startDateTime,
					endDateTime);
		}

		if (type != null && zip == null && hourly == null) {
			return countsForPeriod = callRepository.getCountsByTypeDailyBetweenDatetimes(type, startDateTime,
					endDateTime);
		}

		if (type == null && zip != null && zip.equals("0") && hourly != null) {
			return countsForPeriod = callRepository.getCountsByNullZipHourlyBetweenDatetimes(startDateTime, endDateTime);
		}

		if (type == null && zip != null && zip.equals("0") && hourly == null) {
			return countsForPeriod = callRepository.getCountsByNullZipDailyBetweenDatetimes(startDateTime, endDateTime);
		}

		if (type == null && zip != null && zip.equals("0") == false && hourly != null) {
			return countsForPeriod = callRepository.getCountsByZipHourlyBetweenDatetimes(zip, startDateTime,
					endDateTime);
		}

		if (type == null && zip != null && zip.equals("0") == false && hourly == null) {
			return countsForPeriod = callRepository.getCountsByZipDailyBetweenDatetimes(zip, startDateTime,
					endDateTime);
		}

		if (type == null && zip == null && hourly != null) {
			return countsForPeriod = callRepository.getCountsHourlyBetweenDatetimes(startDateTime, endDateTime);
		}

		return countsForPeriod = callRepository.getCountsDailyBetweenDatetimes(startDateTime, endDateTime);
	}

	@RequestMapping(value = "/count/per-type/{startDate}/{endDate}", method = RequestMethod.GET, produces = "application/json")
	public List<?> getCountsPerTypeForPeriod(@PathVariable String startDate, @PathVariable String endDate,
			@RequestParam(value = "zip", required = false) String zip) {

		LocalDateTime startDateTime;
		LocalDateTime endDateTime;
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			startDateTime = LocalDateTime.from(LocalDate.parse(startDate, formatter).atStartOfDay());
			endDateTime = LocalDateTime.from(LocalDate.parse(endDate, formatter).atStartOfDay().plusDays(1L));
		} catch (DateTimeParseException exception) {
			throw new BadRequestException("Start Date and End Date must follow YYYY-MM-DD format");
		}

		List<?> countsForPeriod;

		if (zip != null) {
			if (zip.equals("0")) {
				countsForPeriod = callRepository.getCountsPerTypeByNullZipBetweenDatetimes(startDateTime, endDateTime);
			} else {
				countsForPeriod = callRepository.getCountsPerTypeByZipBetweenDatetimes(zip, startDateTime, endDateTime);
			}
		} else {
			countsForPeriod = callRepository.getCountsPerTypeBetweenDatetimes(startDateTime, endDateTime);
		}

		return countsForPeriod;
	}

	@RequestMapping(value = "/count/per-zip/{startDate}/{endDate}", method = RequestMethod.GET, produces = "application/json")
	public List<?> getCountsPerZipForPeriod(@PathVariable String startDate, @PathVariable String endDate,
			@RequestParam(value = "type", required = false) String type) {

		LocalDateTime startDateTime;
		LocalDateTime endDateTime;
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			startDateTime = LocalDateTime.from(LocalDate.parse(startDate, formatter).atStartOfDay());
			endDateTime = LocalDateTime.from(LocalDate.parse(endDate, formatter).atStartOfDay().plusDays(1L));
		} catch (DateTimeParseException exception) {
			throw new BadRequestException("Start Date and End Date must follow YYYY-MM-DD format");
		}

		List<?> countsForPeriod;

		if (type != null) {
			countsForPeriod = callRepository.getCountsPerZipByTypeBetweenDatetimes(type, startDateTime, endDateTime);
		} else {
			countsForPeriod = callRepository.getCountsPerZipBetweenDatetimes(startDateTime, endDateTime);
		}

		return countsForPeriod;
	}

	@RequestMapping(value = "/count/{days}", method = RequestMethod.GET, produces = "application/json")
	public List<?> getCountsForPeriod(@PathVariable Long days,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "zip", required = false) String zip) {

		LocalDateTime dateTime = LocalDateTime.now().minusDays(days);

		List<?> countsForPeriod;

		if (type != null && zip != null) {
			countsForPeriod = callRepository.getCountsByTypeAndZipDailySinceDatetime(type, zip, dateTime);
		} else if (type != null && zip == null) {
			countsForPeriod = callRepository.getCountsByTypeDailySinceDatetime(type, dateTime);
		} else if (type == null && zip != null) {
			countsForPeriod = callRepository.getCountsByZipDailySinceDatetime(zip, dateTime);
		} else {
			countsForPeriod = callRepository.getCountsDailySinceDatetime(dateTime);
		}

		return countsForPeriod;
	}

	@RequestMapping(value = "/count/per-type/{days}", method = RequestMethod.GET, produces = "application/json")
	public List<?> getCountsPerTypeForPeriod(@PathVariable Long days,
			@RequestParam(value = "zip", required = false) String zip) {

		LocalDateTime dateTime = LocalDateTime.now().minusDays(days);

		List<?> countsForPeriod;

		if (zip != null) {
			countsForPeriod = callRepository.getCountsPerTypeByZipSinceDatetime(zip, dateTime);
		} else {
			countsForPeriod = callRepository.getCountsPerTypeSinceDatetime(dateTime);
		}

		return countsForPeriod;
	}

	@RequestMapping(value = "/count/per-zip/{days}", method = RequestMethod.GET, produces = "application/json")
	public List<?> getCountsPerZipForPeriod(@PathVariable Long days,
			@RequestParam(value = "type", required = false) String type) {

		LocalDateTime dateTime = LocalDateTime.now().minusDays(days);

		List<?> countsForPeriod;

		if (type != null) {
			countsForPeriod = callRepository.getCountsPerZipByTypeSinceDatetime(type, dateTime);
		} else {
			countsForPeriod = callRepository.getCountsPerZipSinceDatetime(dateTime);
		}

		return countsForPeriod;
	}

}
