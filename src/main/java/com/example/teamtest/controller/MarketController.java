package com.example.teamtest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.teamtest.Repository.MarketRepository;
import com.example.teamtest.Repository.MyItemRepository;
import com.example.teamtest.Repository.UserRepository;
import com.example.teamtest.domain.Grade;
import com.example.teamtest.domain.ItemCategory;
import com.example.teamtest.domain.entity.MarketEntity;
import com.example.teamtest.domain.entity.MyItemEntity;
import com.example.teamtest.domain.entity.UserEntity;
import com.example.teamtest.service.MarketService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/market")
public class MarketController {
	
	private final MarketService marketService;
	private final MyItemRepository myItemRepository;
	private final UserRepository userRepository;
	private final MarketRepository marketRepository;

	@GetMapping // 아이템 목록 조회
	public ResponseEntity<?> getItems() {
		return ResponseEntity.ok(marketService.getAllItems());
	}

	// 임시 DTO
	public static class PurchaseRequest {
		private Long userId;
		private Long itemId;
		
		public Long getUserId() {return userId;}
		public void setUserId(Long userId) {this.userId = userId;}
		
		public Long getItemId() {return itemId;}
		public void setItemId(Long itemId) {this.itemId = itemId;}
	}
	
	// 아이템 구매
	@PostMapping("/purchase")
	public ResponseEntity<?> purchaseItem(@RequestBody PurchaseRequest request) {

	    try {
	      marketService.purchaseItem(request.getUserId(), request.getItemId());
	      return ResponseEntity.ok("구매 완료");
	    } catch (RuntimeException e) {
	        return ResponseEntity.badRequest().body(e.getMessage());
	      }
	}
	
	// 내 아이템 조회
	@GetMapping("my/{userId}")
	public ResponseEntity<?> getMyItems(@PathVariable Long userId){
		List<MyItemEntity> myItems = myItemRepository.findByOwner(
				userRepository.findById(userId)
					.orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다"))
		);
	
		return ResponseEntity.ok(myItems);
	}

	// 환불
	@PostMapping("my/refund/{myItemId}")
	public ResponseEntity<?> refundItem(@PathVariable Long myItemId) {
		try {
			MyItemEntity myItem = myItemRepository.findById(myItemId)
						.orElseThrow(() -> new RuntimeException("아이템을 찾을 수 없습니다"));
			
			UserEntity owner = myItem.getOwner();
			owner.setPoint(owner.getPoint() + myItem.getItem().getItemPrice());
			userRepository.save(owner);
			
			myItemRepository.delete(myItem);
			return ResponseEntity.ok("환불 완료");
		} catch (RuntimeException e) {
	        return ResponseEntity.badRequest().body(e.getMessage());
	    }
	}
	
	// 등급 업데이트
	@PostMapping("use/{userId}/{itemId}") 
	public ResponseEntity<?> useGrade(@PathVariable Long userId, @PathVariable Long itemId) {
		UserEntity user = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다"));
		
		MarketEntity item = marketRepository.findById(itemId)
				.orElseThrow(() -> new RuntimeException("아이템을 찾을 수 없습니다"));
		
		// GRADE 아이템 사용시
		if(item.getItemCategory() == ItemCategory.GRADE) {
			// GRADE 아이템이면 UserEntity.grade 업데이트
			user.setGrade(item.getItemGrade());
	    }  
		// IMAGE 아이템이면 effectImage 적용
		if(item.getItemCategory() == ItemCategory.IMAGE) {
			user.setNicknameBg(item.getEffectImage());
	    }
		userRepository.save(user);
		
		return ResponseEntity.ok(user);
	}

}
