package com.example.nintendoswitchdiscountsbot.dto;

import java.time.Instant;
import java.util.Optional;

import com.example.nintendoswitchdiscountsbot.enums.Country;

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
