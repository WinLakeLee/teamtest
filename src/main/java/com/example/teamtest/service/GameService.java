package com.example.teamtest.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.example.teamtest.Repository.CategoryRepository;
import com.example.teamtest.Repository.QuizRepository;
import com.example.teamtest.Repository.DailyQuizRepository;
import com.example.teamtest.Repository.UserRepository;
import com.example.teamtest.domain.Game;
import com.example.teamtest.domain.DTO.QuizQuestionDTO;
import com.example.teamtest.domain.entity.CategoryEntity;
import com.example.teamtest.domain.entity.DailyQuiz;
import com.example.teamtest.domain.entity.QuizEntity;
import com.example.teamtest.domain.entity.UserEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GameService {

	private final QuizRepository quizRepository;
	private final CategoryRepository categoryRepository;
	private final DailyQuizRepository dailyQuizRepository;
	private final UserRepository userRepository;
	
	/**
	 * 
	 * @param map 
	 * @return
	 */
	public QuizQuestionDTO generateQuiz(String game, String username) {
		
	    QuizQuestionDTO dto = new QuizQuestionDTO();
	    // 전체 카테고리 조회
	    List<CategoryEntity> categoryList = categoryRepository.findAll();
	    CategoryEntity category = categoryList.stream()
	            .filter(c -> c.getGamename().equals(Enum.valueOf(Game.class, game)))
	            .findAny()
	            .orElse(null);

	    List<QuizEntity> allQuizzes = quizRepository.findAllByCategory(category);

	    UserEntity user = userRepository.findByUsername(username).orElseThrow();
	    
	    DailyQuiz dailyQuiz = dailyQuizRepository.findByUser(
	    		userRepository.findByUsername(username).orElseThrow())
	    		.orElseGet(() -> {
	    			DailyQuiz newDailyQuiz = new DailyQuiz();
	    			newDailyQuiz.setUser(user);
	    			newDailyQuiz.setQuizList(new ArrayList<>());
	    			return dailyQuizRepository.save(newDailyQuiz);
	    			}
	    		);

	    List<Integer> answeredQuizIds = dailyQuiz.getQuizList();
	    
	    List<QuizEntity> newQuizzes = allQuizzes.stream()
	            .filter(quiz -> !answeredQuizIds.contains(quiz.getQuizId().intValue()))
	            .collect(Collectors.toList());
	    Collections.shuffle(newQuizzes);
	    System.out.println(newQuizzes);
	    
	    QuizEntity mainQuiz = newQuizzes.stream()
	    		.distinct()
	    		.findAny()
	    		.orElse(null);
	    answered.addLast(mainQuiz.getQuizId().intValue());
	    System.out.println(answered);
	    dailyQuiz.setQuizList(answered);
	    dailyQuizRepository.save(dailyQuiz);
	    
	    dto.setQuizId(mainQuiz.getQuizId());
	    dto.setQuestion(mainQuiz.getQuestion());

	    // 문제에서 정답 추출
	    List<String> answerList = new ArrayList<>();
	    answerList.add(mainQuiz.getAnswer());

	    // 다른 문제들로부터 정답 추출
	    List<String> otherAnswers = 
	        quizRepository.findAllByCategory(mainQuiz.getCategory()).stream()
	            .map(QuizEntity::getAnswer)
	            .filter(answer -> !answer.equals(mainQuiz.getAnswer()))
	            .collect(Collectors.toList());

	    // 정답 리스트에 다른 정답들 추가
	    otherAnswers.stream()
	        .limit(3)
	        .forEach(answerList::add);

	    // 정답 리스트 섞기
	    Collections.shuffle(answerList);
	    dto.setAnswer(answerList);

	    return dto;
	}
	/**
	 * 
	 * @param map id, answer 받아옴
	 * @return
	 */
	public Integer resolve(Map<?, ?> map) {
		// 받아온 아이디로 퀴즈 조회
		QuizEntity quiz = quizRepository.findById((Long)map.get("id")).orElseThrow();
		// 정답을 맞출 시 점수 반환
		if(quiz.getAnswer().equals(map.get("answer")))
			return Integer.valueOf(quiz.getScore());
		else 
			return 0;
	}

}
