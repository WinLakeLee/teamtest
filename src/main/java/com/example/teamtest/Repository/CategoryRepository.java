package com.example.teamtest.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.teamtest.domain.entity.CategoryEntity;
import com.example.teamtest.domain.Game;
import com.example.teamtest.domain.QuestionType;
import java.util.List;





public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer>{
	Optional<CategoryEntity> findByGamenameAndDescription(Game gamename, QuestionType description);
	List<CategoryEntity> findAllByGamename(Game gamename);
}
