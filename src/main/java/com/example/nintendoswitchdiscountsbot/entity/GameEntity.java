package com.example.nintendoswitchdiscountsbot.entity;

import com.example.nintendoswitchdiscountsbot.enums.Country;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

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

    @Column(name = "name_hash")
    private Integer nameHash;

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
