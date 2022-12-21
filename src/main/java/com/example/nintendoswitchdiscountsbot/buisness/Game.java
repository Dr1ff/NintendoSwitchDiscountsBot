package com.example.nintendoswitchdiscountsbot.buisness;

import java.math.BigDecimal;


public record Game(Long id, String name, BigDecimal price, String region) {
}
