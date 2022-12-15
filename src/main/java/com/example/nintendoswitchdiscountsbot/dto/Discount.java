package com.example.nintendoswitchdiscountsbot.dto;

import com.example.nintendoswitchdiscountsbot.entity.DiscountEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Discount implements Serializable {
    private Long id;
    private Long gameId;
    private BigDecimal priceWithDiscount;

    public Discount(DiscountEntity discountEntity) {
        this.id = discountEntity.getId();
        this.gameId = discountEntity.getGameId();
        this.priceWithDiscount = discountEntity.getPriceWithDiscount();
    }
}
