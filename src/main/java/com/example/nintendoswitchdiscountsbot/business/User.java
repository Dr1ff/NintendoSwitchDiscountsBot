package com.example.nintendoswitchdiscountsbot.business;

import com.example.nintendoswitchdiscountsbot.enums.Country;
import lombok.Builder;

import java.util.List;

public record User(
        Long id,
        List<Game> wishlist,
        Country country
) {
    @Builder(toBuilder = true)
    public User {}
}
