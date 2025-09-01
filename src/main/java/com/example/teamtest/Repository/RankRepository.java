package com.example.teamtest.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.teamtest.domain.entity.Rank;

public interface RankRepository extends JpaRepository<Rank, Long> {
	Optional<Rank> findByUserId(Long userId);
	List<Rank> findAllByOrderByLolScoreDesc();
	List<Rank> findAllByOrderByBgScoreDesc();
	List<Rank> findAllByOrderByScScoreDesc();
	List<Rank> findAllByOrderByMsScoreDesc();
}
