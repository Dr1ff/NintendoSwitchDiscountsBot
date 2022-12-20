package com.example.nintendoswitchdiscountsbot.service.storage;

import com.example.nintendoswitchdiscountsbot.buisness.Discount;
import com.example.nintendoswitchdiscountsbot.entity.DiscountEntity;
import com.example.nintendoswitchdiscountsbot.repository.DiscountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiscountStorageService {
    private final DiscountRepository repository;

    public Optional<Discount> findById(Long id) {
        var discountEntityO = repository.findById(id);
        if (discountEntityO.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(fromEntity(discountEntityO.get()));
        }
    }

    private Discount fromEntity(DiscountEntity entity) {
        return new Discount(entity.getId(), entity.getGameId(), entity.getPriceWithDiscount());
    }

    public void add(Discount discount) {
        repository.save(new DiscountEntity(discount.id(), discount.gameId(), discount.priceWithDiscount()));
    }

    public void delete(Discount discount) {
        repository.delete(new DiscountEntity(discount.id(), discount.gameId(), discount.priceWithDiscount()));
    }


}
