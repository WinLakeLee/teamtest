package com.example.teamtest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.teamtest.domain.entity.QuizEntity;
import com.example.teamtest.service.BgroundService;

@RestController
@RequestMapping("quiz")
public class QuizController {

	@Autowired
	private BgroundService bgroundService;

	@PostMapping("/game")
	public ResponseEntity<?> generateQuiz(@RequestBody QuizEntity quiz) {
		return null;
	}
}
