package com.example.nintendoswitchdiscountsbot.dto;

import com.example.nintendoswitchdiscountsbot.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private Long id;
    // тут видимо нужен другой тип, тк будет много id(до 8 тысяч) может List<GameEntity>
    private Long wishlist;
    private String region;

    public User(UserEntity user) {
        this.id = user.getId();
        this.wishlist = user.getWishlist();
        this.region = user.getRegion();
    }
}
