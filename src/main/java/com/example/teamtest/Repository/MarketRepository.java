package com.example.teamtest.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.teamtest.domain.entity.MarketEntity;

@Repository
public interface MarketRepository extends JpaRepository<MarketEntity, Long>{
}
