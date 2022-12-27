package com.example.nintendoswitchdiscountsbot.business;

import java.math.BigDecimal;


public record Game(
        Long id,
        String name,
        BigDecimal price,
        String region
) {
}
