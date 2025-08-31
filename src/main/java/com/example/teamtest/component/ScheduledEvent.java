package com.example.teamtest.component;

import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.teamtest.Repository.GameListRepository;
import com.example.teamtest.Repository.RankRepository;
import com.example.teamtest.Repository.UserRepository;
import com.example.teamtest.domain.entity.GameList;
import com.example.teamtest.domain.entity.RankingEntity;
import com.example.teamtest.domain.entity.UserEntity;
import com.example.teamtest.filter.GameFilter;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Component
@Getter
@Setter
@RequiredArgsConstructor
public class ScheduledEvent {

	private final GameFilter gameFilter;	
	private final RankRepository rankRepository;
	private final UserRepository userRepository;
	private final GameListRepository gameListRepository;
	
	@Scheduled(cron = "0 0 * * * ?")
	public void hourlyEvent() {
		userRepository.findAll().stream()
				.sorted(Comparator.comparing(UserEntity::getDailyScore).reversed())
				.limit(20)
				.map(UserEntity::getId)
				.map((Function<Long, RankingEntity>) id-> {
					return rankRepository.save(new RankingEntity(id));
					})
				.close();
	}
	
	@Scheduled(cron = "0 0 0 * * ?")
	public ConcurrentHashMap<?, ?> dailyEvent() {
        ConcurrentHashMap<String, Integer> attempts = gameFilter.getAttempts();
        attempts.clear();
        return attempts;
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
