package com.example.teamtest.component;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.teamtest.Repository.UserRepository;
import com.example.teamtest.domain.entity.UserEntity;

@Component
public class ScheduledEvent {

	@Autowired
	private UserRepository userRepository;
	
	@Scheduled(cron = "0 0 0 * * ?")
	public void DailyEvent() {
		List<UserEntity> userList = userRepository.findAll();	
		userRepository.saveAll(userList.stream().map(user -> {
				Integer score = user.getDailyScore();
				user.setPoint(user.getPoint() + score);
				user.setDailyScore(0);
				return user;
				})
			.collect(Collectors.toList()
			));
	}

}
