package com.example.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.entity.RefreshToken;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {

}
