package com.example.teamtest.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.teamtest.domain.entity.DailyQuiz;
import com.example.teamtest.domain.entity.UserEntity;


public interface DailyQuizRepository extends JpaRepository<DailyQuiz, Long>{
	Optional<DailyQuiz> findByUser(UserEntity user);
}
