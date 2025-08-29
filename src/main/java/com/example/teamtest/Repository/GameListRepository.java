package com.example.teamtest.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.teamtest.domain.entity.GameList;

public interface GameListRepository extends JpaRepository<GameList, Long> {

}
