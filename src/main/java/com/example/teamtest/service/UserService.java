package com.example.teamtest.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.teamtest.Repository.UserRepository;
import com.example.teamtest.domain.Grade;
import com.example.teamtest.domain.OAuthType;
import com.example.teamtest.domain.DTO.UserDTO;
import com.example.teamtest.domain.entity.UserEntity;
import com.example.teamtest.jwt.JwtService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	private final KakaoLoginService kakaoLoginService;
		
	public HttpHeaders login(UserDTO userDTO) throws AuthenticationException {
		UsernamePasswordAuthenticationToken cred = new UsernamePasswordAuthenticationToken(userDTO.getUsername(),
				userDTO.getPassword());
		Authentication auth = authenticationManager.authenticate(cred);
		String jwt = jwtService.createToken(auth.getName(), auth.getAuthorities());
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(jwt);
		headers.setAccessControlExposeHeaders(List.of("Authorization"));
		return headers;
	}
	
	public HttpHeaders kakaoLogin(String code) {
		String accessToken = kakaoLoginService.getAccessToken(code);
		UserEntity userInfo = kakaoLoginService.getUserInfo(accessToken);
		UserEntity entity = insertOrGet(userInfo);
		UserDTO dto = from(entity);
		HttpHeaders headers = login(dto);
		return headers;
	}
	
	public UserEntity insertOrGet(UserEntity entity) {
		 return userRepository.findByUsername(entity.getUsername())
			        .orElseGet(() -> {
			        	entity.setPassword(passwordEncoder.encode(entity.getPassword()));
						return userRepository.save(entity);
		});
	}
	// 회원가입 서비스
	public UserEntity insert(UserDTO dto) {
		if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
			throw new IllegalArgumentException("이미 존재하는 아이디입니다");
		}
		UserEntity entity = new UserEntity();
		entity.setUsername(dto.getUsername());
		entity.setNickname(dto.getNickname());
		entity.setEmail(dto.getEmail());
		entity.setPassword(passwordEncoder.encode(dto.getPassword()));
		entity.setGrade(Grade.BRONZE);
		entity.setCount(0);
		entity.setPoint(0);
		entity.setOAuthType(OAuthType.BOARD);
		return userRepository.save(entity);
	}
	
	public UserEntity insert(UserEntity entity) {
		return userRepository.save(entity);
	}

	public Optional<UserEntity> findByUsername(String username){
		return userRepository.findByUsername(username);
	}
	
	public UserEntity save(UserEntity user) {
        return userRepository.save(user);
    }
	
	// 회원정보 조회
	@Transactional
	public UserEntity getUser(String username) {
		return userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("해당 유저가 없습니다: " + username));
	}

	// 회원정보 수정
	@Transactional
	public UserEntity update(String username, UserDTO dto) {
		UserEntity findUser = userRepository.findByUsername(username)
				.orElseThrow(() -> new EntityNotFoundException("해당 ID의 유저가 없습니다."));
		findUser.setNickname(dto.getNickname());
		findUser.setPassword(passwordEncoder.encode(dto.getPassword()));
		return userRepository.save(findUser);
	}

	public UserEntity saveOrGet(UserEntity entity) {
		return userRepository.findByUsername(entity.getUsername()).orElseGet(() -> userRepository.save(entity));
	}
	
	// 회원탈퇴
	@Transactional
	public boolean delete(Authentication auth, String password) {
		UserEntity user = userRepository.findByUsername(auth.getName()).get();
		boolean result = passwordEncoder.matches(password, user.getPassword());
		try {
			if (result)
				userRepository.deleteById(user.getId());
			else
				throw new EntityNotFoundException();
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
		}

		return result;
	}
	
	public UserDTO from(UserEntity user) {
		UserDTO dto = UserDTO.builder().id(user.getId()).username(user.getUsername()).nickname(user.getNickname())
				.email(user.getEmail()).password(user.getPassword()).point(user.getPoint()).grade(user.getGrade()).nicknameBg(user.getNicknameBg()).build();
		return dto;
	}

}