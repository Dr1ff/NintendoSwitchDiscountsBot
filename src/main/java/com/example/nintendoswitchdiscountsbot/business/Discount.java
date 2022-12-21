package com.example.nintendoswitchdiscountsbot.business;

import java.math.BigDecimal;


public record Discount(Long id, Long gameId, BigDecimal priceWithDiscount) {
}
