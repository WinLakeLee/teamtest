package com.example.teamtest.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.teamtest.domain.entity.CategoryEntity;
import com.example.teamtest.domain.Game;
import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer>{
	List<CategoryEntity> findAllByGamename(Game gamename);
}
