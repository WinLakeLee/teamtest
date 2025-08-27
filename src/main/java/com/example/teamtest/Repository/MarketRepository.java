package com.example.teamtest.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.teamtest.domain.entity.MarketEntity;

public interface MarketRepository extends JpaRepository<MarketEntity, Long>{

}
