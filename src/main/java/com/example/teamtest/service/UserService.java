package com.example.teamtest.service;


import javax.security.sasl.AuthenticationException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.teamtest.Repository.UserRepository;

import com.example.teamtest.domain.entity.UserEntity;

import jakarta.persistence.EntityNotFoundException;
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
	
}
