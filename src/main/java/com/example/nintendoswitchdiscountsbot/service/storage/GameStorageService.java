package com.example.nintendoswitchdiscountsbot.service.storage;

import com.example.nintendoswitchdiscountsbot.dto.GameDto;
import com.example.nintendoswitchdiscountsbot.entity.GameEntity;
import com.example.nintendoswitchdiscountsbot.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GameStorageService {
    private GameRepository repository;

    public GameDto get(Long id) {
        Optional<GameEntity> gameO = repository.findById(id);
        if (gameO.isPresent()) {
            return new GameDto(gameO.get());
        } else {
            throw new RuntimeException("Game with this id not found");
        }
    }

    public void add(GameDto game) {
        repository.save(new GameEntity(game));
    }

    public void delete(GameDto game) {
        //не понимаю пока нужна ли проверка isPresent
        GameEntity gameEntity = repository.findById(game.getId()).get();
        repository.delete(gameEntity);
    }

}
