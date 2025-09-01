package com.example.teamtest.Repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.teamtest.domain.entity.GameList;

@Repository
public interface GameListRepository extends JpaRepository<GameList, Long> {
	Optional<GameList> findByUserId(Long userId);
	List<GameList> findAllByOrderByLolScoreDesc();
	List<GameList> findAllByOrderByBgScoreDesc();
	List<GameList> findAllByOrderByScScoreDesc();
	List<GameList> findAllByOrderByMsScoreDesc();