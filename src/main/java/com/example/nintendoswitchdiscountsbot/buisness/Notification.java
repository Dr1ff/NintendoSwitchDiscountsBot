package com.example.nintendoswitchdiscountsbot.buisness;

import java.time.Instant;


public record Notification(Long id, Long userId, Long discountId, Instant created, Instant nextPushDate) {
}

