package com.example.nintendoswitchdiscountsbot.business;

import com.example.nintendoswitchdiscountsbot.enums.Country;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;


public record Game(
        String name,
        Country country,
        BigDecimal actualPrice,
        Optional<BigDecimal> priceWithoutDiscount,
        Optional<Double> discountPercent,
        Optional<Instant> priceValidUntil,
        boolean isDiscount,
        Integer nameHash
) {
    @Builder(toBuilder = true)
    public Game {
    }
}
