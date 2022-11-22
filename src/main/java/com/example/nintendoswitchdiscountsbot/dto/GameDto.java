package com.example.nintendoswitchdiscountsbot.dto;

import com.example.nintendoswitchdiscountsbot.entity.GameEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameDto implements Serializable {
    private Long id;
    private String name;
    private BigDecimal price;
    private String region;

    public GameDto(GameEntity gameEntity) {
        this.id = gameEntity.getId();
        this.name = gameEntity.getName();
        this.price = gameEntity.getPrice();
        this.region = gameEntity.getRegion();
    }
}
