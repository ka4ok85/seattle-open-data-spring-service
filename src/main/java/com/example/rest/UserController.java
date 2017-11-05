package com.example.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.User;
import com.example.repository.UserRepository;

@RestController
@RequestMapping("/users/")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@RequestMapping(value = "/current", method = RequestMethod.GET, produces = "application/json")
	public User getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		User user = userRepository.findByLogin(authentication.getPrincipal()
				.toString());

		return user;
	}
}