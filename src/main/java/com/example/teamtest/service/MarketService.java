package com.example.teamtest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.teamtest.Repository.MarketRepository;
import com.example.teamtest.Repository.UserRepository;
import com.example.teamtest.domain.entity.MarketEntity;
import com.example.teamtest.domain.entity.UserEntity;

@Service
public class MarketService {

	@Autowired
	private MarketRepository marketRepository;	
	@Autowired
	private UserRepository userRepository;
	
	// 상점이용
	public ResponseEntity<?> market() {
		
		return null;
	}
	
}
