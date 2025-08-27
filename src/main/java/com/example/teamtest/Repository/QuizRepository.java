package com.example.teamtest.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.teamtest.domain.entity.QuizEntity;

public interface QuizRepository extends JpaRepository<QuizEntity, Long>{

}
