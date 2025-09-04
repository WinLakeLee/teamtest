package com.example.teamtest.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 최고 점수
@Data
@Entity
@Table(name = "game_list")
@NoArgsConstructor
@AllArgsConstructor
public class GameList {

	@Id
	private Long userId;
	
	@Column
	private Integer lolWeeklyScore;
	
	@Column
	private Integer lolMaxScore;
	
	@Column
	private Integer bgWeeklyScore;
	
	@Column
	private Integer bgMaxScore;
	
	@Column
	private Integer scWeeklyScore;
	
	@Column
	private Integer scMaxScore;
	
	@Column
	private Integer msWeeklyScore;

	@Column
	private Integer msMaxScore;
	
	@Column
	private Integer loaWeeklyScore;

	@Column
	private Integer loaMaxScore;
	
	public int sum() {
		return this.getBgWeeklyScore() + this.getMsWeeklyScore() + this.getLolWeeklyScore() + this.getScWeeklyScore() + this.getLoaWeeklyScore();
	}
	
}
