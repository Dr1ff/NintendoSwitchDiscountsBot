package com.example.nintendoswitchdiscountsbot.service.storage;

import com.example.nintendoswitchdiscountsbot.dto.Game;
import com.example.nintendoswitchdiscountsbot.entity.GameEntity;
import com.example.nintendoswitchdiscountsbot.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GameStorageService {
    private GameRepository repository;

    public Game get(Long id) {
        Optional<GameEntity> gameO = repository.findById(id);
        if (gameO.isPresent()) {
            return new Game(gameO.get());
        } else {
            throw new RuntimeException("Game with this id not found");
        }
    }

    public void add(Game game) {
        repository.save(new GameEntity(game));
    }

    public void delete(Game game) {
        //не понимаю пока нужна ли проверка isPresent
        GameEntity gameEntity = repository.findById(game.getId()).get();
        repository.delete(gameEntity);
    }

}
