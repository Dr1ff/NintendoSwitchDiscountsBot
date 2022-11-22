package com.example.nintendoswitchdiscountsbot.repository;

import com.example.nintendoswitchdiscountsbot.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {
}