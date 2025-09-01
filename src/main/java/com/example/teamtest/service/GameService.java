package com.example.teamtest.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.teamtest.Repository.CategoryRepository;
import com.example.teamtest.Repository.QuizRepository;
import com.example.teamtest.Repository.UserRepository;
import com.example.teamtest.Repository.GameListRepository;
import com.example.teamtest.domain.Game;
import com.example.teamtest.domain.QuestionType;
import com.example.teamtest.domain.DTO.HonorListDTO;
import com.example.teamtest.domain.DTO.QuizQuestionDTO;
import com.example.teamtest.domain.DTO.UserDTO;
import com.example.teamtest.domain.entity.CategoryEntity;
import com.example.teamtest.domain.entity.GameList;
import com.example.teamtest.domain.entity.QuizEntity;
import com.example.teamtest.domain.entity.UserEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GameService {

	private final QuizRepository quizRepository;
	private final CategoryRepository categoryRepository;
	private final GameListRepository gameListRepository;
	private final UserRepository userRepository;
	
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
	
	// 퀴즈별 점수 저장
	@Transactional
	public void addScore(Long userId, String gamename, byte score, UserEntity user) {
		 GameList gameList = gameListRepository.findById(userId).orElse(new GameList(userId, user, 0, 0, 0, 0));
		 
			 
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
	
	

	// 퀴즈별 점수 5등 까지 내림차순으로 출력
	public Map<String, List<HonorListDTO>> getScore() {
	    Map<String, List<HonorListDTO>> scoreMap = new HashMap<>();

	    List<HonorListDTO> lolTop5 = gameListRepository.findAllByOrderByLolScoreDesc()
	            .stream()
	            .limit(5)
	            .map(g -> new HonorListDTO(g.getUser().getNickname(), g.getLolScore())) // User 엔티티 연관관계
	            .toList();
	    scoreMap.put("LOL", lolTop5);

	    List<HonorListDTO> bgTop5 = gameListRepository.findAllByOrderByBgScoreDesc()
	            .stream()
	            .limit(5)
	            .map(g -> new HonorListDTO(g.getUser().getNickname(), g.getBgScore()))
	            .toList();
	    scoreMap.put("BG", bgTop5);

	    List<HonorListDTO> scTop5 = gameListRepository.findAllByOrderByScScoreDesc()
	            .stream()
	            .limit(5)
	            .map(g -> new HonorListDTO(g.getUser().getNickname(), g.getScScore()))
	            .toList();
	    scoreMap.put("SC", scTop5);

	    List<HonorListDTO> msTop5 = gameListRepository.findAllByOrderByMsScoreDesc()
	            .stream()
	            .limit(5)
	            .map(g -> new HonorListDTO(g.getUser().getNickname(), g.getMsScore()))
	            .toList();
	    scoreMap.put("MS", msTop5);

	    return scoreMap;
	}
	
	
}
