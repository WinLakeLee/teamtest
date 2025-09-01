package com.example.teamtest;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.teamtest.Repository.GameListRepository;
import com.example.teamtest.domain.entity.GameList;

@SpringBootTest
class TeamtestApplicationTests {

	@Autowired
	private GameListRepository repository;
	
	@Test
	void contextLoads() {

	}

}
