package com.example.teamtest.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "game_list")
@NoArgsConstructor
@AllArgsConstructor
public class GameList {

	@Id
	private Long userId;
	
	@Column
	private Integer lolScore;
	
	@Column
	private Integer bgScore;
	
	@Column
	private Integer scScore;
	
	@Column
	private Integer loaScore;
}
