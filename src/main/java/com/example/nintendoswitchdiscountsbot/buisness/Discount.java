package com.example.nintendoswitchdiscountsbot.buisness;

import lombok.Data;

import java.math.BigDecimal;

@Data
public record Discount(Long id, Long gameId, BigDecimal priceWithDiscount) {
}
