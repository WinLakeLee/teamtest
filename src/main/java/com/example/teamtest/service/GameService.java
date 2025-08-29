package com.example.teamtest.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.teamtest.Repository.CategoryRepository;
import com.example.teamtest.Repository.GameListRepository;
import com.example.teamtest.Repository.QuizRepository;
import com.example.teamtest.Repository.UserRepository;
import com.example.teamtest.domain.Game;
import com.example.teamtest.domain.DTO.QuizQuestionDTO;
import com.example.teamtest.domain.entity.CategoryEntity;
import com.example.teamtest.domain.entity.QuizEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GameService {

	private final QuizRepository quizRepository;
	private final CategoryRepository categoryRepository;
	
	private static final Integer QUIZ_AMOUNT = 30;

	/**
	 * 문제 생성
	 * @param game 문제를 출제해야 할 게임 이름 
	 * @param username 문제를 푼 사람
	 * @return
	 */
	public List<QuizQuestionDTO> generateQuiz(String game) {
		
		List<Long> answeredQuizIds = new ArrayList<>();
		List<QuizQuestionDTO> quizList = new ArrayList<>();
		
		for(int i = 0; i < QUIZ_AMOUNT; i++) {
		    QuizQuestionDTO dto = new QuizQuestionDTO();
		    // 전체 카테고리 조회
		    CategoryEntity category = categoryRepository.findAll().stream()
		            .filter(c -> c.getGamename().equals(Enum.valueOf(Game.class, game)))
		            .findAny()
		            .orElse(null);
		    // 카테고리의 전체 문제 조회
		    List<QuizEntity> allQuizzes = quizRepository.findAllByCategory(category);
		    // 안 나온 문제 추출
		    List<QuizEntity> newQuizzes = allQuizzes.stream()
		            .filter(quiz -> !answeredQuizIds.contains(quiz.getQuizId()))
		            .collect(Collectors.toList());
		    // 안 나온 문제 섞기
		    Collections.shuffle(newQuizzes);
		    // 문제에 안 나온 문제 중 하나 추가
		    QuizEntity mainQuiz = newQuizzes.stream()
		    		.findAny()
		    		.orElse(null);
		    // 이미 나온 문제에 출제 된 문제 추가
		    answeredQuizIds.add(mainQuiz.getQuizId());
		    // 문제 리스트에 퀴즈 아이디, 문제 추가
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
	
		    quizList.add(dto);
		}
		return quizList;
	}
	/**
	 * 문제 푼 결과
	 * @param map id, answer 받아옴
	 * @return
	 */
	public Integer resolve(Map<?, ?> map) {
		// 게임 푼 유저 조회
		QuizEntity quiz = quizRepository.findById((Long)map.get("id")).orElseThrow();
		// 정답을 맞출 시 점수 반환
		if(quiz.getAnswer().equals(map.get("answer"))) {
			return Integer.valueOf(quiz.getScore());			
		} else {
			return 0;			
		}
	}

}
