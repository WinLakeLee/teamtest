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
		
		List<GameList> lolScore = repository.findAllByOrderByLolScoreDesc();
		lolScore.stream()
	    .limit(2) // 2개만
	    .forEach(System.out::println);
		System.out.println("롤점수");
		
		List<GameList> bgScore = repository.findAllByOrderByBgScoreDesc();
		bgScore.stream()
	    .limit(2) 
	    .forEach(System.out::println);
		System.out.println("배그점수");

		
		List<GameList> scScore = repository.findAllByOrderByScScoreDesc();
		scScore.stream()
	    .limit(2) 
	    .forEach(System.out::println);
		System.out.println("스타점수");

		List<GameList> msScore = repository.findAllByOrderByMsScoreDesc();
		msScore.stream()
	    .limit(2) 
	    .forEach(System.out::println);
		System.out.println("메이플점수");

	}

}
