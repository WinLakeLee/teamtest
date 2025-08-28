package com.example.teamtest.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.teamtest.domain.entity.QuizEntity;
import java.util.List;
import com.example.teamtest.domain.entity.CategoryEntity;



public interface QuizRepository extends JpaRepository<QuizEntity, Long>{
	@Query(value = "SELECT * from quiz q where q.category_id = ? ORDER BY RAND() LIMIT 1", nativeQuery = true)
	QuizEntity findSomeOneByCategory(Integer categoryId);
	List<QuizEntity> findAllByCategory(CategoryEntity category);
}
