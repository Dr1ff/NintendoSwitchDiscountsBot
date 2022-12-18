package com.example.nintendoswitchdiscountsbot.dto;

import com.example.nintendoswitchdiscountsbot.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class User {
    private final Long id;
    private Long wishlist;
    private String region;

    public User(UserEntity user) {
        this.id = user.getId();
        this.wishlist = user.getWishlist();
        this.region = user.getRegion();
    }


}
