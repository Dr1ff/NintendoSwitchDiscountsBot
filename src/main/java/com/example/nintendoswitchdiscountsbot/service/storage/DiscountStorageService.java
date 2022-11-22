package com.example.nintendoswitchdiscountsbot.service.storage;

import com.example.nintendoswitchdiscountsbot.dto.DiscountDto;
import com.example.nintendoswitchdiscountsbot.dto.GameDto;
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

    public DiscountDto get(GameDto game) {
        DiscountEntity discountEntity = find(game);
        return new DiscountDto(discountEntity);
    }

    public void add(GameDto game, BigDecimal priceWithDiscount) {
        repository.save(new DiscountEntity(game.getId(), priceWithDiscount));
    }

    public void delete(GameDto game) {
        DiscountEntity discountEntity = find(game);
        repository.delete(discountEntity);
    }

    private DiscountEntity find(GameDto game) {
        return repository.findByGameId(new GameEntity(game.getId(),
                game.getName(),
                game.getPrice(),
                game.getRegion()));
    }

}
