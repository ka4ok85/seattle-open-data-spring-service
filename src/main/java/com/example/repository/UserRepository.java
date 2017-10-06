package com.example.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {

	User findByLogin(String login);

	User findByLoginAndStatus(String login, String statusEnabled);

}
