package com.example.teamtest.service;

import org.springframework.stereotype.Service;

import com.example.teamtest.Repository.UserRepository;
import com.example.teamtest.domain.Grade;
import com.example.teamtest.domain.UserDTO;
import com.example.teamtest.domain.entity.UserEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	
	public Long insert(UserDTO dto) {
		
		UserEntity entity = new UserEntity();
		entity.setUsername(dto.getUsername());
		entity.setNickname(dto.getNickname());
		entity.setEmail(dto.getEmail());
		entity.setPassword(dto.getPassword());
		entity.setGrade(Grade.BRONZE);
		entity.setCount(0);
		entity.setPoint(0);
		
		return userRepository.save(entity).getId();
	}
	
}
