package com.example.nintendoswitchdiscountsbot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notification")
public class NotificationEntity {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @MapsId
    @JoinColumn(name = "id", nullable = false)
    private Long userId;

    @MapsId
    @JoinColumn(name = "id", nullable = false)
    private Long discountId;

    @Column(name = "created")
    private Instant created;

    @Column(name = "next_push_date")
    private Instant nextPushDate;

}