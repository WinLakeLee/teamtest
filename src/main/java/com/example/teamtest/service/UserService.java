package com.example.teamtest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.teamtest.Repository.UserRepository;
import com.example.teamtest.domain.Grade;
import com.example.teamtest.domain.entity.UserEntity;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public void insert(UserEntity user) {
		user.setPassword(null);
		user.setGrade(Grade.BRONZE);
		user.setCount(0);
		user.setPoint(0);
		
		userRepository.save(user);
	}
	
}
