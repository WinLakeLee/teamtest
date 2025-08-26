package com.example.teamtest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.teamtest.domain.entity.UserEntity;
import com.example.teamtest.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping("/signup")
	public ResponseEntity<?> singup (@RequestBody UserEntity user) {
		userService.insert(user);
		
		return new ResponseEntity<>("회원가입 성공", HttpStatus.OK);
	}
	
}
