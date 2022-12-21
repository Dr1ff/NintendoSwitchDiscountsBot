package com.example.nintendoswitchdiscountsbot.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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

    @Column(name = "region")
    private String region;

}