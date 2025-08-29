package com.example.teamtest.controller;

import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.teamtest.jwt.JwtService;
import lombok.RequiredArgsConstructor;

import com.example.teamtest.domain.DTO.UserDTO;
import com.example.teamtest.domain.entity.UserEntity;
import com.example.teamtest.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;

	// 회원가입
	@PostMapping("signup")
	public ResponseEntity<?> signup (@RequestBody UserDTO user) {
		System.out.println(user.toString());
		Long id = userService.insert(user).getId();
		
		return new ResponseEntity<>("회원가입 성공, 유저번호는" + id + "입니다" , HttpStatus.OK);
	}
	
	
	// 회원정보 수정
	@PutMapping("/update")
	public ResponseEntity<?> updateUser(Authentication auth, @RequestBody UserDTO dto) {
		UserEntity user = userService.update(auth.getName(), dto);
		return ResponseEntity.ok(user);
	}
	
	// 회원탈퇴
	@DeleteMapping("delete")
	public ResponseEntity<?> deleteUser(Authentication auth, @RequestBody Map<?, ?> map) {
		userService.delete(auth.getName(), map.get("password").toString());
		return ResponseEntity.ok(null);
	}
	
	// 로그인
	@PostMapping("login")
	public ResponseEntity<?> login(@RequestBody UserDTO userDTO) {
		UsernamePasswordAuthenticationToken cred = new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword());
		Authentication auth = authenticationManager.authenticate(cred);
		String jwt = jwtService.createToken(auth.getName(), auth.getAuthorities());
		return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt).header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Authorization").build();

	}
	
	@PostMapping("userinfo")
	public ResponseEntity<?> userInfo(Authentication auth) {
		return ResponseEntity.ok(userService.getUser(auth.getName()));
	}
	
}