package com.example.nintendoswitchdiscountsbot.service.storage;

import java.util.List;
import java.util.Optional;

import com.example.nintendoswitchdiscountsbot.business.Game;
import com.example.nintendoswitchdiscountsbot.entity.GameEntity;
import com.example.nintendoswitchdiscountsbot.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public void add(Game game) {
        repository.save(toEntity(game));
    }

    public void delete(Game game) {
        repository.delete(toEntity(game));
    }

    public void addBatch(List<Game> games) {
        repository.saveAll(games.stream().map(this::toEntity).toList());
    }

    private GameEntity toEntity(Game game) {
        return new GameEntity(
                new GameEntity.Id(game.name(), game.country()),
                game.actualPrice(),
                game.priceWithoutDiscount().orElse(null),
                game.discountPercent().orElse(null),
                game.priceValidUntil().orElse(null),
                game.isDiscount()
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
                entity.isDiscount()
        );
    }
}
