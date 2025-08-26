package com.example.teamtest.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.EntityNotFoundException;

@RestController
@ControllerAdvice 

public class UserExceptionHandler {

	
	@ExceptionHandler(value = EntityNotFoundException.class)
	public ResponseEntity<?> globalExceptionHandler(EntityNotFoundException e) {
		return new ResponseEntity<>("비밀번호가 틀렸습니다.", HttpStatus.BAD_REQUEST);
		
	}


}


