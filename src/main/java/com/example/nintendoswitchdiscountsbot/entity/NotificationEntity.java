package com.example.nintendoswitchdiscountsbot.entity;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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