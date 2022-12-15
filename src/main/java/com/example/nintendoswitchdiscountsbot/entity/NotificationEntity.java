package com.example.nintendoswitchdiscountsbot.entity;

import com.example.nintendoswitchdiscountsbot.dto.Notification;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
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

    public NotificationEntity(Notification notification) {
        this.id = notification.getId();
        this.userId = notification.getUserId();
        this.discountId = notification.getDiscountId();
        this.created = notification.getCreated();
        this.nextPushDate = notification.getNextPushDate();
    }

}