package com.example.teamtest.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.example.teamtest.Repository.CategoryRepository;
import com.example.teamtest.Repository.QuizRepository;
import com.example.teamtest.Repository.UserRepository;
import com.example.teamtest.domain.entity.UserEntity;
import com.example.teamtest.gameSession.GameSession;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BgroundService {

    private final UserRepository userRepository;
	private final QuizRepository quizRepository;
	private final CategoryRepository categoryRepository;
	private final Map<String, GameSession> sessionMap = new ConcurrentHashMap<>();

	// 점수 DB저장
	public void saveSessionScore(GameSession session) {
		
		UserEntity user = userRepository.findByUsername(session.getUsername()).orElse(null);

		Integer savePoint = user.getPoint(); // 기존 저장된 포인트
		int saveScore = session.getScore(); // 세션에 저장된 점수
		// point에 score 저장
		if(savePoint == null) {
			savePoint = 0;
		} else {
			savePoint += saveScore;
			user.setPoint(savePoint);
		}
		
		userRepository.save(user);
		
	}

}
