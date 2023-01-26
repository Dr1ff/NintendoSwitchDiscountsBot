package com.example.nintendoswitchdiscountsbot.repository;

import com.example.nintendoswitchdiscountsbot.entity.GameEntity;
import com.example.nintendoswitchdiscountsbot.enums.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<GameEntity, GameEntity.Id> {

    List<GameEntity> findAllByIdNameContainingIgnoreCaseAndIdCountry (String name, Country country);

    Optional<GameEntity> findByNameHash(Integer nameHash);
}