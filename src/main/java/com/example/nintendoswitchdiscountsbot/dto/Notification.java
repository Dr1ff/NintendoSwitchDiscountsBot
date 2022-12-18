package com.example.nintendoswitchdiscountsbot.dto;

import com.example.nintendoswitchdiscountsbot.entity.NotificationEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    private Long id;
    private Long userId;
    private Long discountId;
    private Instant created;
    private Instant nextPushDate;

    public Notification(NotificationEntity notification) {
        this.id = notification.getId();
        this.userId = notification.getUserId();
        this.discountId = notification.getDiscountId();
        this.created = notification.getCreated();
        this.nextPushDate = notification.getNextPushDate();
    }
}
