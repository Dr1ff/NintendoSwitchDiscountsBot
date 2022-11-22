package com.example.nintendoswitchdiscountsbot.entity;

import com.example.nintendoswitchdiscountsbot.dto.GameDto;
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
@Table(name = "game")
public class GameEntity {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @Lob
    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private BigDecimal price;

    @Lob
    @Column(name = "region")
    private String region;

    public GameEntity(GameDto gameDto) {
        this.id = gameDto.getId();
        this.name = gameDto.getName();
        this.price = gameDto.getPrice();
        this.region = gameDto.getRegion();
    }
}