package com.example.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/")
public class AdminController {

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/data", method = RequestMethod.GET, produces = "application/json")
	public List<String> getData() {
		List<String> data = new ArrayList<String>();
		data.add("SeCrEt!");

		return data;
	}

}
