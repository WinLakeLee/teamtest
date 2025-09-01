package com.example.teamtest.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.teamtest.Repository.CategoryRepository;
import com.example.teamtest.Repository.GameListRepository;
import com.example.teamtest.Repository.QuizRepository;
import com.example.teamtest.Repository.UserRepository;
import com.example.teamtest.Repository.GameListRepository;
import com.example.teamtest.domain.Game;
import com.example.teamtest.domain.QuestionType;
import com.example.teamtest.domain.DTO.HonorListDTO;
import com.example.teamtest.domain.Game;
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

	private final UserRepository userRepository;
	private final QuizRepository quizRepository;
	private final CategoryRepository categoryRepository;
	private final GameListRepository gameListRepository;
	private final UserRepository userRepository;
	
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

	public Integer result(Map<?, ?> map) {
		Integer score = (Integer)map.get("score");
		System.out.println(score);
		UserEntity user = userRepository.findByUsername((String)(map.get("username"))).orElseThrow();
		System.out.println(user);
		GameList gameList = gameListRepository.findById(user.getId()).orElseGet(() -> new GameList(user.getId(), 0, 0, 0, 0, 0, 0, 0, 0));
		System.out.println(gameList);
		switch ((String)map.get("game")) {
		case "lol":
			gameList.setLolWeeklyScore(gameList.getLolWeeklyScore()+score);
			if(gameList.getLolMaxScore() < score) {
				gameList.setLolMaxScore(score);
			}
			gameListRepository.save(gameList);
			return score;
		case "ms":
			gameList.setMsWeeklyScore(gameList.getMsWeeklyScore()+score);
			if(gameList.getMsMaxScore() < score) {
				gameList.setMsMaxScore(score);
			}
			gameListRepository.save(gameList);
			return score;
		case "bg":
			gameList.setBgWeeklyScore(gameList.getBgWeeklyScore()+score);
			if(gameList.getBgMaxScore() < score) {
				gameList.setBgMaxScore(score);
			}
			gameListRepository.save(gameList);
			return score;
		case "sc":
			gameList.setScWeeklyScore(gameList.getScWeeklyScore()+score);
			if(gameList.getScMaxScore() < score) {
				gameList.setScMaxScore(score);
			}
			gameListRepository.save(gameList);
			return score;

		default:
			return null;
		}

	}

}
