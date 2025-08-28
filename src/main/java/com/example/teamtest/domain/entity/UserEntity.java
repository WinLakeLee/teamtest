package com.example.teamtest.domain.entity;

import java.util.ArrayList;
import java.util.List;

import com.example.teamtest.domain.Grade;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="user")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true, columnDefinition = "varchar(20)")
	private String username;
	
	@Column(nullable = false)
	private String nickname;
	
	@Column(nullable = false, columnDefinition = "varchar(100)")
	private String password;
	
	@Column(nullable = false, unique = true)
	private String email;
	
	@Column
	private Integer count;
	
	@Column
	@Enumerated(EnumType.STRING)
	private Grade grade;
	
	@Column
	private Integer point;
	
	@OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<MyItemEntity> items;
	
	
}
