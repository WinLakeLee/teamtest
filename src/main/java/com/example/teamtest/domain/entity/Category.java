package com.example.teamtest.domain.entity;

import com.example.teamtest.domain.Game;
import com.example.teamtest.domain.QuestionType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
public class Category {

	@Id
	private Integer id;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Game gamename;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private QuestionType describe;

}