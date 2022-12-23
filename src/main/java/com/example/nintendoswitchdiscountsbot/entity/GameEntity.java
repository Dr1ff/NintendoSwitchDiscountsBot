package com.example.nintendoswitchdiscountsbot.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.example.nintendoswitchdiscountsbot.enums.Country;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "game")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GameEntity {

    @EmbeddedId
    private Id id;

    @Column(name = "actual_price", nullable = false)
    private BigDecimal actualPrice;

    @Column(name = "price_without_discount")
    private BigDecimal priceWithoutDiscount;

    @Column(name = "discount_percent")
    private Double discountPercent;

    @Column(name = "price_valid_until")
    private Instant priceValidUntil;

    @Column(name = "is_discount", nullable = false)
    private boolean isDiscount;

    @Getter
    @Embeddable
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Id implements Serializable {
        @Column(name = "name", nullable = false)
        private String name;

        @Enumerated(EnumType.STRING)
        @Column(name = "country", nullable = false)
        private Country country;
    }
}