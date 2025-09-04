package com.example.teamtest.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.teamtest.domain.DTO.UserDTO;
import com.example.teamtest.domain.entity.UserEntity;
import com.example.teamtest.jwt.JwtService;
import com.example.teamtest.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	private final JwtService jwtService;
	
	// 회원가입
	@PostMapping("signup")
	public ResponseEntity<?> signup(@RequestBody UserDTO user) {
		System.out.println(user.toString());
		Long id = userService.insert(user).getId();

		return new ResponseEntity<>("회원가입 성공, 유저번호는" + id + "입니다", HttpStatus.OK);
	}

	// 회원정보 조회
	@GetMapping("/userinfo")
	public ResponseEntity<UserDTO> userinfo(Authentication auth) {
		UserEntity user = userService.getUser(auth.getName());
		UserDTO dto = userService.from(user);
		return ResponseEntity.ok(dto);
	}

	// 회원정보 수정
	@PutMapping("/update")
	public ResponseEntity<?> updateUser(Authentication auth, @RequestBody UserDTO dto) {

	    String username = auth.getName();
	    UserEntity updated = userService.update(username, dto);
	    return ResponseEntity.ok(updated);
	}

	// 회원탈퇴
	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteUser(Authentication auth, @RequestParam String password) {
		boolean result = userService.delete(auth, password);

		if (result)
			return ResponseEntity.ok("회원탈퇴 완료");
		else
			return ResponseEntity.ok("비밀번호 오류");
	}
	
	// 로그인
	@PostMapping("login")
	public ResponseEntity<?> login(@RequestBody UserDTO userDTO) {
		HttpHeaders headers = userService.login(userDTO);
		return new ResponseEntity<>(headers, HttpStatus.OK);
	}

	@PostMapping("/oauth/kakao")
	public ResponseEntity<?> kakaologin(@RequestBody String username) {
		System.out.println(username);
		
		String jwts = jwtService.createToken(username, null);
		
		return ResponseEntity.ok()
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + jwts)
				.header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Authorization")
				.build();
	}
}
