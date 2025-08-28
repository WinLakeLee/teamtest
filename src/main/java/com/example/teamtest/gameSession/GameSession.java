package com.example.teamtest.gameSession;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@RequiredArgsConstructor
public class GameSession {

	private final String username; // 로그인 유저 아이디
	private int score = 0; // 갱신점수
	private Set<Long> usedQuestionIds = new HashSet<>(); // 사용중인 문제 id
	private long startTimeMillis = System.currentTimeMillis(); // 게임시작 시간  
	private LocalDate date = LocalDate.of(2025, 9, 1);
	private int gamePlay = 0;  // 당일날 게임횟수
	
	// 점수갱신
	public void updateScore(int newScore) {
		if ( newScore > this.score ) {
			this.score = newScore;
		}
	}
	
	// 오늘 플레이 횟수
	public void playCount() {
		this.gamePlay++;
	}
	
	// 새로운날 초기화
	public void resetDate() {
		this.date = LocalDate.now();
		this.gamePlay = 0;
		this.score = 0;
	}
	
	// 게임 시작 시간 초기화
	public void resetGameTime() {
		this.startTimeMillis = System.currentTimeMillis();
	}
	
}
