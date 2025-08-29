package com.example.teamtest.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import com.example.teamtest.domain.entity.GameList;
import com.example.teamtest.service.GameService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class GameController {

	private final GameService gameService;

	@GetMapping("/honor")
	public ResponseEntity<Map<String, List<GameList>>> getTopScores() {
		System.out.println("sada");
	    return ResponseEntity.ok(gameService.getScore());
	}
}
