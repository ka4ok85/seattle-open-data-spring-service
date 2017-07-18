package com.example.exceptions.handlers;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.exceptions.dto.ErrorDetail;
import com.example.exceptions.BadRequestException;

@ControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<?> handleResouceNotFoundException(BadRequestException badRequestException,
			HttpServletRequest request) {

		ErrorDetail errorDetail = new ErrorDetail();
		errorDetail.setDetail(badRequestException.getMessage());
		errorDetail.setDeveloperMessage(badRequestException.getClass().getName());
		errorDetail.setStatus(HttpStatus.BAD_REQUEST.value());
		errorDetail.setTimestamp(new Date().getTime());
		errorDetail.setTitle("Bad Request");

		return new ResponseEntity<>(errorDetail, HttpStatus.NOT_FOUND);
	}

}
