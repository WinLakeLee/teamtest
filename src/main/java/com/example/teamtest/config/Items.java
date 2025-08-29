package com.example.teamtest.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.teamtest.Repository.MarketRepository;
import com.example.teamtest.domain.ItemCategory;
import com.example.teamtest.domain.entity.MarketEntity;

@Configuration						
public class Items {
	
	@Bean
	CommandLineRunner initItems(MarketRepository marketRepository) {
	// 스프링 시작할 때 자동실행	
		
		// 아이템 예시
		return args -> {
			if(marketRepository.count() == 0) {
			MarketEntity item1 = new MarketEntity(null, "실버", 100, ItemCategory.GRADE, "border: 2px solid #c0c0c0;");
            MarketEntity item2 = new MarketEntity(null, "골드", 150, ItemCategory.GRADE, "border: 2px solid gold;");
            MarketEntity item3 = new MarketEntity(null, "플레티넘", 200, ItemCategory.GRADE, "border: 2px solid #00bfff;");
            MarketEntity item4 = new MarketEntity(null, "다이아", 250, ItemCategory.GRADE, "border: 2px solid #1492cc;");
            
            MarketEntity item5 = new MarketEntity(null, "불꽃배경", 100, ItemCategory.IMAGE, "background-image: url('/images/fire.gif'); background-size: cover;");
            MarketEntity item6 = new MarketEntity(null, "별빛배경", 150, ItemCategory.IMAGE, "background-image: url('/images/star.gif'); background-size: cover;");
            
            marketRepository.save(item1);
            marketRepository.save(item2);
            marketRepository.save(item3);
            marketRepository.save(item4);
            marketRepository.save(item5);
            marketRepository.save(item6);

			}
		};	
	}
};