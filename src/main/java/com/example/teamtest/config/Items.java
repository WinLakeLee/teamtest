package com.example.teamtest.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.teamtest.Repository.MarketRepository;
import com.example.teamtest.domain.entity.MarketEntity;

@Configuration						
public class Items {
	
	@Bean
	CommandLineRunner initItems(MarketRepository marketRepository) {
	// 스프링 시작할 때 자동실행	
		
		// 아이템 예시
		return args -> {
			MarketEntity item1 = new MarketEntity(null, "빨간 포션", 100);
            MarketEntity item2 = new MarketEntity(null, "파란 포션", 150);
            MarketEntity item3 = new MarketEntity(null, "검은 검", 500);
            MarketEntity item4 = new MarketEntity(null, "방패", 300);
            
            marketRepository.save(item1);
            marketRepository.save(item2);
            marketRepository.save(item3);
            marketRepository.save(item4);
		};
	}
	
	
	
}