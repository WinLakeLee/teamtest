package com.example.teamtest.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.teamtest.service.QuizService;

@RestController
public class QuizController {

	@Autowired
	private QuizService quizService;
	
	@PostMapping("quiz/{game}")
	public ResponseEntity<?> generateQuiz(@PathVariable String game) {
		System.out.println(game);
		return ResponseEntity.ok(quizService.generateQuiz(game.toUpperCase()));
	}
	
	@PostMapping("score")
	public ResponseEntity<?> resolve(@RequestBody Map<?, ?> map) {
		Integer score = quizService.resolve(map);
		return ResponseEntity.ok(score);
	}
	
	@PostMapping("result")
	public ResponseEntity<?> result(@RequestBody Map<?, ?> map) {
		int score = quizService.result(map);
		return ResponseEntity.ok(score);
	}
	
}
