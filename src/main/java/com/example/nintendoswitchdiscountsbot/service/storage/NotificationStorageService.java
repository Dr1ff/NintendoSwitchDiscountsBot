package com.example.nintendoswitchdiscountsbot.service.storage;

import com.example.nintendoswitchdiscountsbot.buisness.Notification;
import com.example.nintendoswitchdiscountsbot.entity.NotificationEntity;
import com.example.nintendoswitchdiscountsbot.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationStorageService {
    private NotificationRepository repository;

    public Optional<Notification> findById(Long id) {
        var notificationEntityO = repository.findById(id);
        if (notificationEntityO.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(fromEntity(notificationEntityO.get()));
        }
    }

    public void add(Notification notification) {
        repository.save(new NotificationEntity(
                notification.id(),
                notification.userId(),
                notification.discountId(),
                notification.created(),
                notification.nextPushDate()));
    }

    public void delete(Notification notification) {
        repository.delete(new NotificationEntity(
                notification.id(),
                notification.userId(),
                notification.discountId(),
                notification.created(),
                notification.nextPushDate()));
    }

    private Notification fromEntity(NotificationEntity entity) {
        return new Notification(entity.getId(),
                                entity.getUserId(),
                                entity.getDiscountId(),
                                entity.getCreated(),
                                entity.getNextPushDate());
    }
}
