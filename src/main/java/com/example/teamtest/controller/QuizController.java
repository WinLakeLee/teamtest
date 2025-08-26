package com.example.teamtest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.teamtest.domain.entity.Quiz;

@RestController
@RequestMapping("quiz")
public class QuizController {

//	@Autowired
//	private QuizService quizService;

	@PostMapping
	public ResponseEntity<?> generateQuiz(@RequestBody Quiz quiz) {
		return null;
	}
}
