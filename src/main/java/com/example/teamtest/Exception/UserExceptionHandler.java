package com.example.teamtest.Exception;

import javax.naming.CannotProceedException;

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
	public ResponseEntity<?> entityNotFoundExceptionHandler(EntityNotFoundException e) {
		return new ResponseEntity<>("비밀번호가 틀렸습니다.", HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = CannotProceedException.class)
	public ResponseEntity<?> cannotProceedExceptionHandler(CannotProceedException e) {
		return new ResponseEntity<>("입장 횟수를 모두 사용했습니다", HttpStatus.TOO_MANY_REQUESTS);
	}


}


