package com.example.teamtest.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.teamtest.domain.UserDTO;
import com.example.teamtest.jwt.JwtService;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
public class UserController {

	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;

	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UserDTO userDTO){
		UsernamePasswordAuthenticationToken cred = new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword());
		Authentication auth = authenticationManager.authenticate(cred);
		String jwt = jwtService.createToken(auth.getName(), auth.getAuthorities());
		return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt).header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Authorization").build();
		
	}
}
