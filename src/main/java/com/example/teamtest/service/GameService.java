package com.example.teamtest.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.teamtest.Repository.CategoryRepository;
import com.example.teamtest.Repository.QuizRepository;
import com.example.teamtest.Repository.GameListRepository;
import com.example.teamtest.domain.Game;
import com.example.teamtest.domain.QuestionType;
import com.example.teamtest.domain.DTO.QuizQuestionDTO;
import com.example.teamtest.domain.entity.CategoryEntity;
import com.example.teamtest.domain.entity.GameList;
import com.example.teamtest.domain.entity.QuizEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GameService {

	private final QuizRepository quizRepository;
	private final CategoryRepository categoryRepository;
	private final GameListRepository gameListRepository;
	
	/**
	 * 랜덤
	 */
	private Random random;
	/**
	 * 
	 * @param map 
	 * @return
	 */
	public QuizQuestionDTO generateQuiz(Map<?, ?> map) {
		
		QuizQuestionDTO dto = new QuizQuestionDTO();
		List<QuestionType>questionTypes = List.of(QuestionType.values());
		List<String> answerList = new ArrayList<>();
				
		Collections.shuffle(questionTypes);
		
		QuestionType type = questionTypes.get(0);
		// 랜덤 추출을 위한 리스트 생성
		CategoryEntity category = categoryRepository
				.findByGamenameAndDescription(Enum.valueOf(Game.class, (String)map.get("game")), type)
				.orElseThrow();
		// 특정 게임의 모든 문제 추출
		QuizEntity quiz = quizRepository.findSomeOneByCategory(category);
		// dto의 퀴즈 id와 질문 세팅
		dto.setQuizId(quiz.getQuizId());
		dto.setQuestion(quiz.getQuestion());
		// 카테고리에 속하는 답변 리스트 생성
		for(;;) {
			answerList.add(quizRepository.findSomeOneByCategory(category).getAnswer());
			answerList.stream().distinct().collect(Collectors.toList());
			if(answerList.size() == 4) 
				break;
		}
		// 답 리스트에 이미 정답이 있을 시
		if(answerList.contains(quiz.getAnswer())) {
			dto.setAnswer(answerList);
			return dto;
		} else {	
		// 답변 리스트에 정답이 없을 시
			answerList.add(random.nextInt(4), quiz.getAnswer());
			dto.setAnswer(answerList);
			return dto;
		}
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
	
	// 퀴즈별 점수 저장
	@Transactional
	public void addScore(Long userId, String gamename, byte score) {
		 GameList gameList = gameListRepository.findById(userId).orElse(new GameList(userId, 0, 0, 0, 0));
		 
			 
			 switch (gamename.toUpperCase()) {
			 case "LOL":
				 gameList.setLolScore(gameList.getLolScore() + score);
				 break;
			 case "BG":
				 gameList.setBgScore(gameList.getBgScore() + score);
				 break;
			 case "SC":
				 gameList.setScScore(gameList.getScScore() + score);
				 break;
			 case "MS":
				 gameList.setMsScore(gameList.getMsScore() + score);
				 break;
			 default:
				 break;
			 
		 }
     // DB에 저장 (insert 또는 update)
     gameListRepository.save(gameList);
	}

	
	public Map<String, List<GameList>> getScore() {
	    Map<String, List<GameList>> Score = new HashMap<>();

	    List<GameList> lolTop5 = gameListRepository.findAllByOrderByLolScoreDesc()
	            .stream().limit(5).toList();
	    Score.put("LOL", lolTop5);

	    List<GameList> bgTop5 = gameListRepository.findAllByOrderByBgScoreDesc()
	            .stream().limit(5).toList();
	    Score.put("BG", bgTop5);

	    List<GameList> scTop5 = gameListRepository.findAllByOrderByScScoreDesc()
	            .stream().limit(5).toList();
	    Score.put("SC", scTop5);

	    List<GameList> msTop5 = gameListRepository.findAllByOrderByMsScoreDesc()
	            .stream().limit(5).toList();
	    Score.put("MS", msTop5);

	    return Score;
	}
	

}
