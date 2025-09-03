package com.example.teamtest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.teamtest.Repository.MarketRepository;
import com.example.teamtest.Repository.MyItemRepository;
import com.example.teamtest.Repository.UserRepository;
import com.example.teamtest.domain.entity.MarketEntity;
import com.example.teamtest.domain.entity.MyItemEntity;
import com.example.teamtest.domain.entity.UserEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MarketService {

	private final MarketRepository marketRepository;	
	private final UserRepository userRepository;
	private final MyItemRepository myItemRepository;
	
	public void purchaseItem(Long userId, Long itemId) {
		UserEntity user = userRepository.findById(userId)
							.orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다"));
		MarketEntity item = marketRepository.findById(itemId)
							.orElseThrow(() -> new RuntimeException("아이템을 찾을 수 없습니다"));
		
		if(user.getPoint() < item.getItemPrice()) {
			throw new RuntimeException("포인트가 부족합니다");
		}
		
		// 포인트 차감
		user.setPoint(user.getPoint() - item.getItemPrice());
		userRepository.save(user);
		
		// 구매내역 저장
		MyItemEntity myItem = new MyItemEntity();
		myItem.setItem(item);
		myItem.setOwner(user);
		myItemRepository.save(myItem);
		
	}
	
	public List<MarketEntity> getAllItems() {
		return marketRepository.findAll();
	}
	
}
