package com.example.teamtest.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;

import com.example.teamtest.jwt.JwtService;
import lombok.RequiredArgsConstructor;

import com.example.teamtest.domain.DTO.UserDTO;

import com.example.teamtest.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;

	@PostMapping("/signup")
	public ResponseEntity<?> singup (@RequestBody UserDTO user) {
		Long id = userService.insert(user).getId();
		
		return new ResponseEntity<>("회원가입 성공, 유저번호는" + id + "입니다" , HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UserDTO userDTO){
		UsernamePasswordAuthenticationToken cred = new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword());
		Authentication auth = authenticationManager.authenticate(cred);
		String jwt = jwtService.createToken(auth.getName(), auth.getAuthorities());
		return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt).header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Authorization").build();
	}
	

	
}