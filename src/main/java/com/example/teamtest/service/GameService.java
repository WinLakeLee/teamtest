package com.example.teamtest.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.teamtest.Repository.CategoryRepository;
import com.example.teamtest.Repository.QuizRepository;
import com.example.teamtest.domain.Game;
import com.example.teamtest.domain.QuestionType;
import com.example.teamtest.domain.DTO.QuizQuestionDTO;
import com.example.teamtest.domain.entity.CategoryEntity;
import com.example.teamtest.domain.entity.QuizEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GameService {

	private final QuizRepository quizRepository;
	private final CategoryRepository categoryRepository;
	/**
	 * 랜덤
	 */
	private Random random;
	/**
	 * 
	 * @param map 
	 * @return
	 */
	public QuizQuestionDTO generateQuiz(String game) {

	    QuizQuestionDTO dto = new QuizQuestionDTO();
	    
	    // Convert to a mutable list
	    List<CategoryEntity> categoryList = categoryRepository.findAll();
	    
	    CategoryEntity category = categoryList.stream()
	            .filter(c -> c.getGamename().equals(Enum.valueOf(Game.class, game)))
	            .findFirst()
	            .orElse(null);

	    if (category == null) {
	    	return null;
	    }

	    // Find a random quiz question from the selected category
	    QuizEntity mainQuiz = quizRepository.findSomeOneByCategory(category.getCategoryId());

	    if (mainQuiz == null) {
	        return null;
	    }

	    dto.setQuizId(mainQuiz.getQuizId());
	    dto.setQuestion(mainQuiz.getQuestion());
	    dto.setScore(Integer.valueOf(mainQuiz.getScore()));

	    // Create a list for the answers, starting with the correct one
	    List<String> answerList = new ArrayList<>();
	    answerList.add(mainQuiz.getAnswer());

	    // Get a list of ALL answers from the SAME category, excluding the correct one
	    List<String> otherAnswers = 
	        quizRepository.findAllByCategory(mainQuiz.getCategory()).stream()
	            .map(QuizEntity::getAnswer)
	            .filter(answer -> !answer.equals(mainQuiz.getAnswer()))
	            .collect(Collectors.toList());
	    
	    // Shuffle the other answers to pick three random incorrect ones
	    Collections.shuffle(otherAnswers);
	    
	    // Add the first three incorrect answers to our answer list
	    otherAnswers.stream()
	        .limit(3)
	        .forEach(answerList::add);

	    // Shuffle the final list to mix the correct and incorrect answers
	    Collections.shuffle(answerList);
	    
	    dto.setAnswer(answerList);
	    return dto;
	}
	/**
	 * 
	 * @param map id, answer 받아옴
	 * @return
	 */
	public boolean resolve(Map<?, ?> map) {
		// 받아온 아이디로 퀴즈 조회
		QuizEntity quiz = quizRepository.findById((Long)map.get("id")).orElseThrow();
		// 정답을 맞출 시 true 반환
		if(quiz.getAnswer().equals(map.get("answer"))) {
			return true;
		} else {
		// 정답을 틀리거나 없을 시 false 반환
			return false;
		}
	}
}
