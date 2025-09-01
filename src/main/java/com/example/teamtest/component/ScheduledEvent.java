package com.example.teamtest.component;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.teamtest.Repository.GameListRepository;
import com.example.teamtest.Repository.UserRepository;
import com.example.teamtest.filter.GameFilter;
import com.example.teamtest.service.TotalService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Component
@Getter
@Setter
@RequiredArgsConstructor
public class ScheduledEvent {

	private final GameFilter gameFilter;	
	private final UserRepository userRepository;
	private final GameListRepository gameListRepository;
	private TotalService totalService;
	
	@Scheduled(cron = "0 0 * * * ?")
	public void hourlyEvent() {
		
	}
	
	@Scheduled(cron = "0 0 0 * * ?")
	public ConcurrentHashMap<?, ?> dailyEvent() {
        ConcurrentHashMap<String, Integer> attempts = gameFilter.getAttempts();
        attempts.clear();
        return attempts;
	}
	
	@Scheduled(cron = "0 50 23 * * 0")
	public void weeklyEvent() {
		totalService.calculation();
	}

}
