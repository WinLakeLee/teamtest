package com.example.teamtest.domain.entity;

import com.example.teamtest.domain.ItemCategory;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "market")
public class MarketEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long itemId;
	
	@Column(nullable = false)
	private String itemName;
	
	@Column(nullable = false)
	private Integer itemPrice;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private ItemCategory itemCategory;
	
	private String effectClass;
	
	public MarketEntity(Long id, String itemName, int itemPrice, ItemCategory category, String effect) {
        this.itemId = id;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemCategory = category;
        this.effectClass = effect;
    }
	
}
