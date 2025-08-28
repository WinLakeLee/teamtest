package com.example.teamtest.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.teamtest.domain.entity.MyItemEntity;

public interface MyItemRepository extends JpaRepository<MyItemEntity, Long>{

}
