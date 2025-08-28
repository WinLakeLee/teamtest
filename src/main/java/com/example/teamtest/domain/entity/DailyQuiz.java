package com.example.teamtest.domain.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "daily_quiz")
@NoArgsConstructor
@AllArgsConstructor
public class DailyQuiz {
	
	@Id
	@Column(name = "daily_quiz_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long dailyQuizId;

	@JoinColumn(name = "username")
	@OneToOne(fetch = FetchType.EAGER)
	private UserEntity user;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "quiz_list", joinColumns = 
			@JoinColumn(name = "daily_quiz_id")
	)
	private List<Integer> quizList = new ArrayList<>();
	
}
