package com.example.nintendoswitchdiscountsbot.entity;

import com.example.nintendoswitchdiscountsbot.dto.DiscountDto;
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

    public DiscountEntity(DiscountDto discountDto) {
        this.id = discountDto.getId();
        this.gameId = discountDto.getGameId();
        this.priceWithDiscount = discountDto.getPriceWithDiscount();
    }

    public DiscountEntity(Long gameId, BigDecimal priceWithDiscount) {
        this.gameId = gameId;
        this.priceWithDiscount = priceWithDiscount;
    }

}