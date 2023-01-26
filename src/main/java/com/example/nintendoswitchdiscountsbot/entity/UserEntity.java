package com.example.nintendoswitchdiscountsbot.entity;

import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.enums.Country;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "\"user\"")
@TypeDef(name = "jsonb", typeClass = JsonType.class)
public class UserEntity {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Type(type = "jsonb")
    @Column(name = "wishlist", columnDefinition = "jsonb")
    private List<GameEntity.Id> wishlist;

    @Enumerated(EnumType.STRING)
    @Column(name = "country", nullable = false)
    private Country country;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private Command state;

}