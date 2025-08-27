package com.example.teamtest.domain.entity;

import jakarta.persistence.Column;
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
@Table(name = "round")
@NoArgsConstructor
@AllArgsConstructor
public class RoundEntity {

	@Id
	@Column(name = "round_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer roundId;
	
	@JoinColumn(name="user_id", unique = true)
	@OneToOne(fetch = FetchType.EAGER)
	private UserEntity user;
	
	@Column
	private Integer dayOneScore;
	
	@Column
	private Integer dayTwoScore;
	
	@Column
	private Integer dayThreeScore;
}
