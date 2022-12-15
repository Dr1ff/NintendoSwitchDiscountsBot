package com.example.nintendoswitchdiscountsbot.service.storage;

import com.example.nintendoswitchdiscountsbot.dto.Discount;
import com.example.nintendoswitchdiscountsbot.dto.Game;
import com.example.nintendoswitchdiscountsbot.entity.DiscountEntity;
import com.example.nintendoswitchdiscountsbot.entity.GameEntity;
import com.example.nintendoswitchdiscountsbot.repository.DiscountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class DiscountStorageService {
    private DiscountRepository repository;

    public Discount get(Game game) {
        DiscountEntity discountEntity = find(game);
        return new Discount(discountEntity);
    }

    public void add(Game game, BigDecimal priceWithDiscount) {
        repository.save(new DiscountEntity(game.getId(), priceWithDiscount));
    }

    public void delete(Game game) {
        DiscountEntity discountEntity = find(game);
        repository.delete(discountEntity);
    }

    private DiscountEntity find(Game game) {
        return repository.findByGameId(new GameEntity(game.getId(),
                game.getName(),
                game.getPrice(),
                game.getRegion()));
    }

}
