package com.example.nintendoswitchdiscountsbot.repository;

import com.example.nintendoswitchdiscountsbot.entity.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<GameEntity, Long> {
}