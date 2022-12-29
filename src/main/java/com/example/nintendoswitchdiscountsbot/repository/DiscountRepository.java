package com.example.nintendoswitchdiscountsbot.repository;

import com.example.nintendoswitchdiscountsbot.entity.DiscountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountRepository extends JpaRepository<DiscountEntity, Long> {
}