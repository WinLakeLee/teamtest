package com.example.teamtest.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.teamtest.Repository.CategoryRepository;
import com.example.teamtest.Repository.MarketRepository;
import com.example.teamtest.domain.Category;
import com.example.teamtest.domain.Game;
import com.example.teamtest.domain.Grade;
import com.example.teamtest.domain.ItemCategory;
import com.example.teamtest.domain.entity.CategoryEntity;
import com.example.teamtest.domain.entity.MarketEntity;

@Configuration						
public class Items {
	
	@Bean
	CommandLineRunner initItems(MarketRepository marketRepository) {
	// 스프링 시작할 때 자동실행	
		
		// 아이템 예시
		return args -> {
			if(marketRepository.count() == 0) {
			MarketEntity item1 = new MarketEntity(null, "브론즈", 0, ItemCategory.GRADE, Grade.BRONZE,"/images/rank/티어이미지/BRONZE.jpg");
			MarketEntity item2 = new MarketEntity(null, "실버", 100, ItemCategory.GRADE, Grade.SILVER,"/images/rank/티어이미지/SILVER.jpg");
            MarketEntity item3 = new MarketEntity(null, "골드", 150, ItemCategory.GRADE, Grade.GOLD , "/images/rank/티어이미지/GOLD.jpg");
            MarketEntity item4 = new MarketEntity(null, "플레티넘", 200, ItemCategory.GRADE, Grade.PLATINUM, "/images/rank/티어이미지/PLATINUM.jpg");
            MarketEntity item5 = new MarketEntity(null, "다이아", 250, ItemCategory.GRADE, Grade.DIAMOND, "/images/rank/티어이미지/DIAMOND.jpg");
            
            MarketEntity item6 = new MarketEntity(null, "불꽃배경", 300, ItemCategory.IMAGE, null, "/images/effect/테두리이미지/fire.gif");
            MarketEntity item7 = new MarketEntity(null, "나뭇잎배경", 350, ItemCategory.IMAGE, null, "/images/effect/테두리이미지/leaf.gif");
            MarketEntity item8 = new MarketEntity(null, "번개배경", 350, ItemCategory.IMAGE, null, "/images/effect/테두리이미지/lightning.gif");
            MarketEntity item9 = new MarketEntity(null, "네온배경", 400, ItemCategory.IMAGE, null, "/images/effect/테두리이미지/neon.gif");
            MarketEntity item10 = new MarketEntity(null, "연기배경", 400, ItemCategory.IMAGE, null, "/images/effect/테두리이미지/smoke.gif");
            MarketEntity item11 = new MarketEntity(null, "반짝배경", 450, ItemCategory.IMAGE, null, "/images/effect/테두리이미지/Sparkle.gif");
            
            marketRepository.save(item1);
            marketRepository.save(item2);
            marketRepository.save(item3);
            marketRepository.save(item4);
            marketRepository.save(item5);
            marketRepository.save(item6);
            marketRepository.save(item7);
            marketRepository.save(item8);
            marketRepository.save(item9);
            marketRepository.save(item10);
            marketRepository.save(item11);

			}
		};	
	}
	
	@Bean
	CommandLineRunner initCategory(CategoryRepository categoryRepository) {
		return args -> {
			categoryRepository.save(new CategoryEntity(9, Game.LOA, Category.PRS));
			categoryRepository.save(new CategoryEntity(10, Game.LOA, Category.VIL));
			categoryRepository.save(new CategoryEntity(11, Game.LOA, Category.CITY));
			categoryRepository.save(new CategoryEntity(12, Game.LOA, Category.GRE));
			categoryRepository.save(new CategoryEntity(13, Game.LOA, Category.SKL));
			categoryRepository.save(new CategoryEntity(14, Game.LOA, Category.GD));
			categoryRepository.save(new CategoryEntity(15, Game.LOA, Category.IL));
		};
	}
};