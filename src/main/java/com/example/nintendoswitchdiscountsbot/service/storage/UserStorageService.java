package com.example.nintendoswitchdiscountsbot.service.storage;

import com.example.nintendoswitchdiscountsbot.dto.UserDto;
import com.example.nintendoswitchdiscountsbot.entity.UserEntity;
import com.example.nintendoswitchdiscountsbot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserStorageService {
    private UserRepository repository;

    public UserDto get(Long id) {
        return new UserDto(find(id));
    }

    public void add(UserDto user) {
        repository.save(new UserEntity(user));
    }

    public void delete(UserDto user) {
        repository.delete(find(user.getId()));
    }

    private UserEntity find(Long id) {
        Optional<UserEntity> notificationO = repository.findById(id);
        if(notificationO.isPresent()) {
            return notificationO.get();
        } else {
            throw new RuntimeException("User with this id not found");
        }
    }
}
