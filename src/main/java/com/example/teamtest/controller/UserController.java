package com.example.teamtest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.teamtest.domain.UserDTO;

import com.example.teamtest.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	
	@PostMapping("/signup")
	public ResponseEntity<?> singup (@RequestBody UserDTO user) {
		Long id = userService.insert(user);
		
		return new ResponseEntity<>("회원가입 성공, 유저번호는" + id + "입니다" , HttpStatus.OK);
	}
	
}
