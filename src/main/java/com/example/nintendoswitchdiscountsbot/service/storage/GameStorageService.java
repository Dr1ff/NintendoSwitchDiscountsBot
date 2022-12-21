package com.example.nintendoswitchdiscountsbot.service.storage;

import com.example.nintendoswitchdiscountsbot.business.Game;
import com.example.nintendoswitchdiscountsbot.entity.GameEntity;
import com.example.nintendoswitchdiscountsbot.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GameStorageService {
    private final GameRepository repository;

    public Optional<Game> findById(Long id) {
        var gameEntityO = repository.findById(id);
        if (gameEntityO.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(fromEntity(gameEntityO.get()));
        }
    }
    private Game fromEntity(GameEntity entity) {
        return new Game(entity.getId(), entity.getName(), entity.getPrice(), entity.getRegion());
    }

    public void add(Game game) {
        repository.save(new GameEntity(game.id(), game.name(), game.price(), game.region()));
    }

    public void delete(Game game) {
        repository.delete(new GameEntity(game.id(), game.name(), game.price(), game.region()));
    }

}
