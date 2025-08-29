package com.example.teamtest.component;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.teamtest.Repository.GameListRepository;
import com.example.teamtest.Repository.UserRepository;
import com.example.teamtest.domain.entity.GameList;
import com.example.teamtest.domain.entity.UserEntity;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ScheduledEvent {

	private final UserRepository userRepository;
	private final GameListRepository gameListRepository;
	
	@Scheduled(cron = "0 0 * * * ?")
	public void hourlyEvent() {
		List<Long> Ranking = userRepository.findAll().stream()
				.sorted(Comparator.comparing(UserEntity::getDailyScore))
				.limit(20)
				.map(UserEntity::getId)
				.collect(Collectors.toList());
	}
	
	@Scheduled(cron = "0 0 0 * * ?")
	public void dailyEvent() {
		
	}
	
	@Scheduled(cron = "0 50 23 * * 0")
	public void weeklyEvent() {
		Map<Long, Integer> UserScores = gameListRepository
				.findAll()
				.stream()
				.collect(Collectors.groupingBy(GameList::getUserId, 
							Collectors.summingInt(GameList::sum)));
		UserScores.forEach((userId, sum) -> userRepository
				.findById(userId).stream()
				.map(entity -> {
					entity.setPoint(entity.getPoint() + sum);
					entity.setLastWeekScore(sum);
					entity.setTotalScore(entity.getTotalScore() + sum);
					return userRepository.save(entity);
					})
				.close());
	}

}
