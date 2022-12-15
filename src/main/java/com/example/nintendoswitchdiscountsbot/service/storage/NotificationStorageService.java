package com.example.nintendoswitchdiscountsbot.service.storage;

import com.example.nintendoswitchdiscountsbot.dto.Notification;
import com.example.nintendoswitchdiscountsbot.entity.NotificationEntity;
import com.example.nintendoswitchdiscountsbot.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationStorageService {
    private NotificationRepository repository;

    public Notification get(Long id) {
        return new Notification(find(id));
    }

    public void add(Notification notification) {
        repository.save(new NotificationEntity(notification));
    }

    public void delete(Notification notification) {
        repository.delete(find(notification.getId()));
    }

    private NotificationEntity find(Long id) {
        Optional<NotificationEntity> notificationO = repository.findById(id);
        if(notificationO.isPresent()) {
            return notificationO.get();
        } else {
            throw new RuntimeException("Notification with this id not found");
        }
    }
}
