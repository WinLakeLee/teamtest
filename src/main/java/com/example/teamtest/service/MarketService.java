package com.example.teamtest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.teamtest.Repository.MarketRepository;

@Service
public class MarketService {

	@Autowired
	private MarketRepository marketRepository;	
	
}
