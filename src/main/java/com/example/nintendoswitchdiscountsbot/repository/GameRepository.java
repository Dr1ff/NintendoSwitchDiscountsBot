package com.example.nintendoswitchdiscountsbot.repository;

import com.example.nintendoswitchdiscountsbot.entity.GameEntity;
import com.example.nintendoswitchdiscountsbot.enums.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<GameEntity, GameEntity.Id> {

    List<GameEntity> findAllByIdNameContainingIgnoreCaseAndIdCountryContaining(String name, Country country);
}