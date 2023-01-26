package com.example.nintendoswitchdiscountsbot.business;

import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.enums.Country;
import lombok.Builder;

import java.util.List;

public record User(
        Long id,
        List<Game> wishlist,
        Country country,
        Command state
) {
    @Builder(toBuilder = true)
    public User {}
}
