package com.example.teamtest.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@Table(name = "rank")
@AllArgsConstructor
public class RankingEntity {

	@Id
	Long rank;

	
}
