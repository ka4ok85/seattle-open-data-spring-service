package com.example.rest;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.repository.CallRepository;

@RestController
@RequestMapping("/calls/")
public class CallController {

	@Autowired
	private CallRepository callRepository;

	@RequestMapping(value = "/count/{days}", method = RequestMethod.GET, produces = "application/json")
	public List<?> getCountsForPeriod(@PathVariable Long days) {
		LocalDateTime dateTime = LocalDateTime.now().minusDays(days);
		List<?> countsForPeriod = callRepository.getCountsDailySinceDatetime(dateTime);

		return countsForPeriod;
	}

	@RequestMapping(value = "/count/per-type/{days}", method = RequestMethod.GET, produces = "application/json")
	public List<?> getCountsPerTypeForPeriod(@PathVariable Long days) {
		LocalDateTime dateTime = LocalDateTime.now().minusDays(days);
		List<?> countsForPeriod = callRepository.getCountsPerTypeSinceDatetime(dateTime);

		return countsForPeriod;
	}
}
