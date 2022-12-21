package com.example.nintendoswitchdiscountsbot.business;

import java.time.Instant;


public record Notification(Long id, Long userId, Long discountId, Instant created, Instant nextPushDate) {
}