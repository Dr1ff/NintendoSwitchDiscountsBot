package com.example.nintendoswitchdiscountsbot.service.storage;

import com.example.nintendoswitchdiscountsbot.business.Game;
import com.example.nintendoswitchdiscountsbot.entity.GameEntity;
import com.example.nintendoswitchdiscountsbot.enums.Country;
import com.example.nintendoswitchdiscountsbot.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GameStorageService {
    private final GameRepository repository;

    public Optional<Game> findById(GameEntity.Id id) {
        var gameEntityO = repository.findById(id);
        if (gameEntityO.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(fromEntity(gameEntityO.get()));
        }
    }

    public Optional<Game> findByHashcode(Integer hash) {
        var gameEntityO = repository.findByHashcode(hash);
        if (gameEntityO.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(fromEntity(gameEntityO.get()));
        }
    }

    public void add(Game game) {
        repository.save(toEntity(game));
    }

    public void delete(Game game) {
        repository.delete(toEntity(game));
    }

    public void addBatch(List<Game> games) {
        repository.saveAll(games.stream().map(this::toEntity).toList());
    }

    public List<Game> findAllByRequestAndCountry(String request, Country country) {
        var entityList = repository
                .findById_NameContainsIgnoreCaseAndId_Country(request, country);
        if (entityList.isEmpty()) {
            return List.of();
        } else {
            return entityList
                    .stream()
                    .map(this::fromEntity)
                    .toList();
        }
    }

    private GameEntity toEntity(Game game) {
        return new GameEntity(
                new GameEntity.Id(game.name(), game.country()),
                game.actualPrice(),
                game.priceWithoutDiscount().orElse(null),
                game.discountPercent().orElse(null),
                game.priceValidUntil().orElse(null),
                game.isDiscount(),
                game.hashcode()
        );
    }

    private Game fromEntity(GameEntity entity) {
        return new Game(
                entity.getId().getName(),
                entity.getId().getCountry(),
                entity.getActualPrice(),
                Optional.ofNullable(entity.getPriceWithoutDiscount()),
                Optional.ofNullable(entity.getDiscountPercent()),
                Optional.ofNullable(entity.getPriceValidUntil()),
                entity.isDiscount(),
                entity.getHashcode()
        );
    }
}
