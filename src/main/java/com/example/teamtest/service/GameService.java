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
import com.example.teamtest.domain.entity.GameList;
import com.example.teamtest.domain.entity.QuizEntity;
import com.example.teamtest.domain.entity.UserEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GameService {

	private final UserRepository userRepository;
	private final QuizRepository quizRepository;
	private final CategoryRepository categoryRepository;
	private final GameListRepository gameListRepository;
	/**
	 * 문제 수
	 */
	private static final Integer QUIZ_AMOUNT = 50;
	/**
	 * 문제 생성
	 * @param game 문제를 출제해야 할 게임 이름 
	 * @param username 문제를 푼 사람
	 * @return 문제 리스트
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
		    dto.setPoint(Integer.valueOf(mainQuiz.getScore()));

		    // 문제에서 정답 추출
		    List<String> answerList = new ArrayList<>();
		    answerList.add(mainQuiz.getAnswer());

		    // 모든 문제 섞기
		    Collections.shuffle(allQuizzes);
		    
		    // 정답 리스트에 다른 정답들 추가
		    allQuizzes.stream()
		    	.map(QuizEntity::getAnswer)
		        .filter(answer -> !answer.equals(mainQuiz.getAnswer()))
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
		QuizEntity quiz = quizRepository.findById(Long.valueOf((Integer)map.get("id"))).orElseThrow();
		if(quiz.getAnswer().equals(map.get("answer").toString())) {
			return Integer.valueOf(quiz.getScore());			
		} else {
			return 0;			
		}
	}

	public Integer result(Map<?, ?> map) {
		Integer score = (Integer)map.get("score");
		UserEntity user = userRepository.findByUsername((String)(map.get("username"))).orElseThrow();
		GameList gameList = gameListRepository.findById(user.getId()).orElseThrow();
		switch ((String)map.get("game")) {
		case "lol":
			gameList.setLolWeeklyScore(gameList.getLolWeeklyScore()+score);
			if(gameList.getLolMaxScore() < score) {
				gameList.setLolMaxScore(score);
			}
			return score;
		case "ms":
			gameList.setMsWeeklyScore(gameList.getMsWeeklyScore()+score);
			if(gameList.getMsMaxScore() < score) {
				gameList.setMsMaxScore(score);
			}
			return score;
		case "bg":
			gameList.setBgWeeklyScore(gameList.getBgWeeklyScore()+score);
			if(gameList.getBgMaxScore() < score) {
				gameList.setBgMaxScore(score);
			}
			return score;
		case "sc":
			gameList.setScWeeklyScore(gameList.getScWeeklyScore()+score);
			if(gameList.getScMaxScore() < score) {
				gameList.setScMaxScore(score);
			}
			return score;

		default:
			return null;
		}

	}

}
