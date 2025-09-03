package com.example.teamtest.controller;

import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.teamtest.jwt.JwtService;
import lombok.RequiredArgsConstructor;

import com.example.teamtest.Repository.UserRepository;
import com.example.teamtest.domain.DTO.UserDTO;
import com.example.teamtest.domain.entity.UserEntity;
import com.example.teamtest.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	private final UserRepository userRepository;

	// 회원가입
	@PostMapping("/signup")
	public ResponseEntity<?> signup (@RequestBody UserDTO user) {
		System.out.println(user.toString());
		Long id = userService.insert(user).getId();
		
		return new ResponseEntity<>("회원가입 성공, 유저번호는" + id + "입니다" , HttpStatus.OK);
	}
	
	// 회원정보 조회
	@GetMapping("/userinfo")
	public ResponseEntity<?> userinfo(Authentication auth) {
		UserEntity user = userService.getUser(auth.getName());
		
		return new ResponseEntity<>(user, HttpStatus.OK);
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
		UserEntity user = userRepository.findByUsername(auth.getName()).get();
		boolean result = userService.delete(auth, password);
		
		if(result)
			return ResponseEntity.ok("회원탈퇴 완료");
		else
			return ResponseEntity.badRequest().body("비밀번호 오류");
	}
	
	// 로그인
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UserDTO userDTO) {
		UsernamePasswordAuthenticationToken cred = new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword());
		Authentication auth = authenticationManager.authenticate(cred);
		String jwt = jwtService.createToken(auth.getName(), auth.getAuthorities());
		return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt).header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Authorization").build();

	}
	
	
}
