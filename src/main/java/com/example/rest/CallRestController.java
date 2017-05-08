package com.example.rest;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Call;
import com.example.repository.CallRepository;

@RestController
public class CallRestController {

	@Autowired
	private CallRepository callRepository;
	
	@RequestMapping(method=RequestMethod.GET, value="/calls")
	public Collection<Call> calls() {
		return callRepository.findAll();
	}
	
}
