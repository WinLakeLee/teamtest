package com.example.teamtest.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.teamtest.Repository.UserRepository;
import com.example.teamtest.domain.entity.UserEntity;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity entity = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 유저입니다"));
		return new UserDetailsImpl(entity);
	}
}
