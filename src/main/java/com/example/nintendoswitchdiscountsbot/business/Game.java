package com.example.nintendoswitchdiscountsbot.business;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

import com.example.nintendoswitchdiscountsbot.enums.Country;


public record Game(
        String name,
        Country country,
        BigDecimal actualPrice,
        Optional<BigDecimal> priceWithoutDiscount,
        Optional<Double> discountPercent,
        Optional<Instant> priceValidUntil,
        boolean isDiscount
) {
}
