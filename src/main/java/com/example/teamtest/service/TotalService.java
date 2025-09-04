package com.example.teamtest.service;

import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.teamtest.Repository.GameListRepository;
import com.example.teamtest.Repository.UserRepository;
import com.example.teamtest.domain.entity.GameList;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TotalService {
	
	private final GameListRepository gameListRepository;
	private final UserRepository userRepository;
	
	private static final String[] PW_WORDS = {
		    "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
		    "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
		    "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"
		};
	private static final int PW_LENGTH = 10;
	
	private final Random random = new Random();
	
	public String generatePw() {
		StringBuilder sb = new StringBuilder(PW_LENGTH);
		
		for(int i = 0; i < PW_LENGTH; i++) {
			sb.append(PW_WORDS[random.nextInt(PW_WORDS.length)]);
		}
		return sb.toString();
	}

	public void settlement() {
		gameListRepository.findAll()
		.stream()
		.collect(Collectors.groupingBy(GameList::getUserId, 
					Collectors.summingInt(GameList::sum)))
		.forEach(
			(userId, sum) -> userRepository
				.findById(userId)
				.stream()
				.map(entity -> {
					entity.setPoint(entity.getPoint() + sum);
					entity.setLastWeekScore(sum);
					entity.setTotalScore(entity.getTotalScore() + sum);
					userRepository.save(entity);
					return null;
					})
		.close());
	}

}