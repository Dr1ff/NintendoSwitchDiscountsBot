package com.example.nintendoswitchdiscountsbot.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "discount")
public class DiscountEntity {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    private Long id;

    @MapsId
    @JoinColumn(name = "id")
    private Long gameId;

    @Column(name = "price_with_discount")
    private BigDecimal priceWithDiscount;



}