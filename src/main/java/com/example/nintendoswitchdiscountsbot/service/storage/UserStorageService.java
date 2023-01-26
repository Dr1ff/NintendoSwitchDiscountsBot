package com.example.nintendoswitchdiscountsbot.service.storage;

import com.example.nintendoswitchdiscountsbot.business.Game;
import com.example.nintendoswitchdiscountsbot.business.User;
import com.example.nintendoswitchdiscountsbot.entity.GameEntity;
import com.example.nintendoswitchdiscountsbot.entity.UserEntity;
import com.example.nintendoswitchdiscountsbot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserStorageService {

    private final UserRepository repository;
    private final GameStorageService gameStorageService;

    public Optional<User> findById(Long id) {
        var userEntityO = repository.findById(id);
        if (userEntityO.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(fromEntity(userEntityO.get()));
        }
    }

    public void add(User user) {
        repository.save(toEntity(user));
    }

    public void delete(User user) {
        repository.delete(toEntity(user));
    }

    public void addGame(Long userId, Game game) {
        var UserO = findById(userId);
        List<Game> games = new ArrayList<>();
        UserO.ifPresent(user -> {
            games.addAll(user.wishlist());
            if (!games.contains(game)) {
                games.add(game);
            }
            add(user.toBuilder()
                    .wishlist(games)
                    .build());
        });


    }

    private User fromEntity(UserEntity entity) {
        return new User(
                entity.getId(),
                entity.getWishlist()
                        .stream()
                        .map(gameStorageService::findById)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .toList(),
                entity.getCountry(),
                entity.getState()
        );
    }

    private UserEntity toEntity(User user) {
        return new UserEntity(
                user.id(),
                user.wishlist()
                        .stream()
                        .map(game -> new GameEntity.Id(game.name(), game.country()))
                        .toList(),
                user.country(),
                user.state()
        );
    }
}
