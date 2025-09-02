package com.example.teamtest.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.teamtest.service.GameService;

@RestController
public class QuizController {

	@Autowired
	private GameService gameService;
	
	@PostMapping("quiz/{game}")
	public ResponseEntity<?> generateQuiz(@PathVariable String game) {
		System.out.println(game);
		return ResponseEntity.ok(gameService.generateQuiz(game.toUpperCase()));
	}
	
	@PostMapping("score")
	public ResponseEntity<?> resolve(@RequestBody Map<?, ?> map) {
		Integer score = gameService.resolve(map);
		return ResponseEntity.ok(score);
	}
	
	@PostMapping("result")
	public ResponseEntity<?> result(@RequestBody Map<?, ?> map) {
		int score = gameService.result(map);
		System.out.println("=========================="+score);
		return ResponseEntity.ok(score);
	}
	
}
