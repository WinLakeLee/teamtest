package com.example.teamtest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.teamtest.service.MarketService;

@RestController
@RequestMapping("market")
public class MarketController {
	
	@Autowired
	private MarketService marketService;

	@GetMapping // 아이템 목록 조회
	public ResponseEntity<?> getItems() {
		return ResponseEntity.ok(marketService.getAllItems());
	}

	// 임시 DTO
	public static class PurchaseRequest {
		private Long userId;
		private Long itemId;
		
		private Long getUserId() {return userId;}
		public void setUserId(Long userId) {this.userId = userId;}
		
		private Long getItemId() {return itemId;}
		public void setItemId(Long itemId) {this.itemId = itemId;}
	}
	
	// 아이템 구매
	@PostMapping("purchase")
	public ResponseEntity<?> purchaseItem(@RequestBody PurchaseRequest request) {
	    try {
	      marketService.purchaseItem(request.getUserId(), request.getItemId());
	      return ResponseEntity.ok("구매 완료");
	    } catch (RuntimeException e) {
	        return ResponseEntity.badRequest().body(e.getMessage());
	      }
	}
	
	
	
}
