package com.example.nintendoswitchdiscountsbot.buisness;

import lombok.Data;

import java.math.BigDecimal;

@Data
public record Game(Long id, String name, BigDecimal price, String region) {
}
