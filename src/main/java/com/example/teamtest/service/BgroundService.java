package com.example.teamtest.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.teamtest.Repository.CategoryRepository;
import com.example.teamtest.Repository.QuizRepository;
import com.example.teamtest.gameSesstion.GameSesstion;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BgroundService {

	private final QuizRepository quizRepository;
	private final CategoryRepository categoryRepository;
	private final Map<String, GameSesstion> sessionMap = new ConcurrentHashMap<>();
	
	//게임시작


	
}
