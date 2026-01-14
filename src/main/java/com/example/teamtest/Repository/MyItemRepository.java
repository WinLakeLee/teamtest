package com.example.teamtest.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.teamtest.domain.entity.MyItemEntity;
import com.example.teamtest.domain.entity.UserEntity;

@Repository
public interface MyItemRepository extends JpaRepository<MyItemEntity, Long>{
	List<MyItemEntity> findByOwner(UserEntity owner);
}
