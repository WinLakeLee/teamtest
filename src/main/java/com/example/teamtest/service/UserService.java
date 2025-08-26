package com.example.teamtest.service;

import javax.security.sasl.AuthenticationException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

import com.example.teamtest.domain.Grade;
import com.example.teamtest.Repository.UserRepository;
import com.example.teamtest.domain.DTO.UserDTO;
import com.example.teamtest.domain.entity.UserEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public UserEntity getUser(String username, String password) throws AuthenticationException{
		UserEntity entity = userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 아이디입니다."));

		if(!passwordEncoder.matches(password, entity.getPassword())) {
	            throw new AuthenticationException();
	    }
		return entity;
  }
    
	public Long insert(UserDTO dto) {
		
		UserEntity entity = new UserEntity();
		entity.setUsername(dto.getUsername());
		entity.setNickname(dto.getNickname());
		entity.setEmail(dto.getEmail());
		entity.setPassword(passwordEncoder.encode(dto.getPassword()));
		entity.setGrade(Grade.BRONZE);
		entity.setCount(0);
		entity.setPoint(0);
		
		return userRepository.save(entity).getId();
	}
	
}
