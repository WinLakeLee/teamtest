package com.example.teamtest.service;

import javax.security.sasl.AuthenticationException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

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

	public UserEntity getUser(String username, String password)
//			throws AuthenticationException
	{
		UserEntity entity = userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 아이디입니다."));

		if(password.equals(entity.getPassword())) {
			return null;
//	            throw new AuthenticationException();
	    }
		return entity;
  }
    
	public UserEntity insert(UserDTO dto) {
		
		if(userRepository.findByUsername(dto.getUsername()).isPresent()) {
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
		
		return userRepository.save(entity);
	}
	
	// 회원정보 수정
	@Transactional
	public UserEntity update(String username, UserDTO dto) {
		try {
		UserEntity findUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 유저가 없습니다."));
		
		if(passwordEncoder.matches(dto.getPassword(), findUser.getPassword()))
			userRepository.deleteById(findUser.getId());
		else 
			throw new AuthenticationException();
		findUser.setNickname(dto.getNickname());
		findUser.setPassword(passwordEncoder.encode(dto.getPassword()));
		return userRepository.save(findUser);
		} catch (AuthenticationException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	// 회원탈퇴
	@Transactional
	public void delete(String username, String password) {
		try {UserEntity entity = userRepository.findByUsername(username).orElseThrow();
		if(passwordEncoder.matches(password, entity.getPassword()))
			userRepository.deleteById(entity.getId());
		else 
			throw new AuthenticationException();
		} catch (AuthenticationException e) {
			System.out.println(e.getMessage());
		}
	}
	
}
