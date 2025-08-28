package com.example.teamtest.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.teamtest.domain.entity.DailyQuiz;
import com.example.teamtest.domain.entity.UserEntity;


public interface DailyQuizRepository extends JpaRepository<DailyQuiz, Long>{
	@Query(value = "select * from quiz_list ql join daily_quiz dq where dq.daily_quiz_id in (ql.daily_quiz_id)", nativeQuery = true)
	List<Integer>findQuizListByUsername(String username);
	Optional<DailyQuiz> findByUser(UserEntity user);
}
