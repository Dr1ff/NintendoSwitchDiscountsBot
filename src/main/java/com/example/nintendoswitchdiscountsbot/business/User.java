package com.example.nintendoswitchdiscountsbot.business;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class User { // Можно все таки сделать рекордом с билдером

    private final Long id;
    private Long wishlist;
    private final String country;
}
