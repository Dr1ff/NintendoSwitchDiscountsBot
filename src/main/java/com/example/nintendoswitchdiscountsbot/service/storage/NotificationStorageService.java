package com.example.nintendoswitchdiscountsbot.service.storage;

import com.example.nintendoswitchdiscountsbot.dto.NotificationDto;
import com.example.nintendoswitchdiscountsbot.entity.NotificationEntity;
import com.example.nintendoswitchdiscountsbot.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationStorageService {
    private NotificationRepository repository;

    public NotificationDto get(Long id) {
        return new NotificationDto(find(id));
    }

    public void add(NotificationDto notification) {
        repository.save(new NotificationEntity(notification));
    }

    public void delete(NotificationDto notification) {
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
