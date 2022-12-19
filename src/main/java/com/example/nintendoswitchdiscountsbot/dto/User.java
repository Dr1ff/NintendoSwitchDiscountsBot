package com.example.nintendoswitchdiscountsbot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class User {
    private final Long id;
    private Long wishlist;
    private final String region;

}
