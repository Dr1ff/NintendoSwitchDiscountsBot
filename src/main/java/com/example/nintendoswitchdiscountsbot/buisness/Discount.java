package com.example.nintendoswitchdiscountsbot.buisness;

import java.math.BigDecimal;


public record Discount(Long id, Long gameId, BigDecimal priceWithDiscount) {
}
