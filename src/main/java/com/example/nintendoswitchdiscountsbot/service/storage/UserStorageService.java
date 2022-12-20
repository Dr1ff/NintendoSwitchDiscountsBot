package com.example.nintendoswitchdiscountsbot.service.storage;

import com.example.nintendoswitchdiscountsbot.buisness.User;
import com.example.nintendoswitchdiscountsbot.entity.UserEntity;
import com.example.nintendoswitchdiscountsbot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserStorageService {
    private final UserRepository repository;

    public Optional<User> findById(Long id) {
        var userEntityO = repository.findById(id);
        if (userEntityO.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(fromEntity(userEntityO.get()));
        }
    }

    private User fromEntity(UserEntity entity) {
        return new User(entity.getId(), entity.getWishlist(), entity.getRegion());
    }

    public void add(User user) {
        repository.save(new UserEntity(user.getId(), user.getWishlist(), user.getRegion()));
    }

    public void delete(User user) {
        repository.delete(new UserEntity(user.getId(), user.getWishlist(), user.getRegion()));
    }
}
