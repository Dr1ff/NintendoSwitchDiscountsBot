package com.example.nintendoswitchdiscountsbot.buisness;

import lombok.Data;

import java.time.Instant;

@Data
public record Notification(Long id, Long userId, Long discountId, Instant created, Instant nextPushDate) {
}

