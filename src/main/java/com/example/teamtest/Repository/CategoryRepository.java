package com.example.teamtest.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.teamtest.domain.entity.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer>{

}
