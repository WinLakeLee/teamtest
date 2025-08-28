package com.example.teamtest.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.teamtest.service.GameService;

@RestController
@RequestMapping("quiz")
public class QuizController {

	@Autowired
	private GameService gameService;
	
	@PostMapping("{game}")
	public ResponseEntity<?> generateQuiz(@PathVariable String game) {
		return ResponseEntity.ok(gameService.generateQuiz(game.toUpperCase()));
	}
	
	@PostMapping
	public ResponseEntity<?> resolve(@RequestBody Map<?, ?> map) {
		return ResponseEntity.ok(resolve(map));
	}
	
}
