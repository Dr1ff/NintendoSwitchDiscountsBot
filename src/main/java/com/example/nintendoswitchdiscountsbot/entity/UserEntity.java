package com.example.nintendoswitchdiscountsbot.entity;

import com.example.nintendoswitchdiscountsbot.dto.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "\"user\"")
public class UserEntity {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "wishlist")
    private Long wishlist;

    @Lob
    @Column(name = "region")
    private String region;

    public UserEntity(User user) {
        this.id = user.getId();
        this.wishlist = user.getWishlist();
        this.region = user.getRegion();
    }
}