package com.example.teamtest.domain.entity;

import com.example.teamtest.domain.Game;
import com.example.teamtest.domain.Category;

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
@Table(name="category")
@NoArgsConstructor
@AllArgsConstructor
public class CategoryEntity {

	@Id
	private Integer categoryId;

	@Column
	@Enumerated(EnumType.STRING)
	private Game gamename;
	
	@Column
	@Enumerated(EnumType.STRING)
	private Category category;
	
}