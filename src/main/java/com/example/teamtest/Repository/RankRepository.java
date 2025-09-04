package com.example.teamtest.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.teamtest.domain.entity.Rank;

@Repository
public interface RankRepository extends JpaRepository<Rank, Long> {
	Optional<Rank> findByUserId(Long userId);
	List<Rank> findAllByOrderByLolScoreDesc();
	List<Rank> findAllByOrderByBgScoreDesc();
	List<Rank> findAllByOrderByScScoreDesc();
	List<Rank> findAllByOrderByMsScoreDesc();
	List<Rank> findAllByOrderByLoaScoreDesc();
}
