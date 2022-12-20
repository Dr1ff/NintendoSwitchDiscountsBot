package com.example.nintendoswitchdiscountsbot.buisness;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    private final Long id;
    private Long wishlist;
    private final String region;

}
