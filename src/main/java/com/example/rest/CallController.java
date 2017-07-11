package com.example.rest;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.repository.CallRepository;

@RestController
@RequestMapping("/calls/")
public class CallController {

	@Autowired
	private CallRepository callRepository;

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
