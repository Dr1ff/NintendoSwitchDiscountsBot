package com.example.nintendoswitchdiscountsbot.dto;

import com.example.nintendoswitchdiscountsbot.enums.Country;

import java.time.Instant;
import java.util.Optional;

/**
 * @author Alexander Popov
 */
public record GameDto(
        String name,
        Country country,
        double actualPrice,
        Optional<Double> priceWithoutDiscount,
        Optional<Double> discountPercent,
        Optional<Instant> priceValidUntil
) {
}
