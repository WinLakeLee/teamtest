package com.example.teamtest.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    private UserEntity user;
    
    @Column
    private Integer lolScore;

    @Column
    private Integer bgScore;

    @Column
    private Integer scScore;

    @Column
    private Integer msScore;
}