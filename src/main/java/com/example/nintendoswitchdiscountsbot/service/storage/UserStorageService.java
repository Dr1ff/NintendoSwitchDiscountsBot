package com.example.nintendoswitchdiscountsbot.service.storage;

import com.example.nintendoswitchdiscountsbot.dto.User;
import com.example.nintendoswitchdiscountsbot.entity.UserEntity;
import com.example.nintendoswitchdiscountsbot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserStorageService {
    private final UserRepository repository;

    public Optional<User> get(Long id) {
        var userEntityO = repository.findById(id);
        if (userEntityO.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(new User(userEntityO.get().getId(),
                    userEntityO.get().getWishlist(),
                    userEntityO.get().getRegion()));
        }
    }

    public void add(User user) {
        repository.save(new UserEntity(user.getId(), user.getWishlist(), user.getRegion()));
    }

    public void delete(User user) {
        repository.delete(new UserEntity(user.getId(), user.getWishlist(), user.getRegion()));
    }
}
