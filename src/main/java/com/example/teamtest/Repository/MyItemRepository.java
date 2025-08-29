package com.example.teamtest.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.teamtest.domain.entity.MyItemEntity;
import com.example.teamtest.domain.entity.UserEntity;

public interface MyItemRepository extends JpaRepository<MyItemEntity, Long>{

	List<MyItemEntity> findByOwner(UserEntity owner);
}
