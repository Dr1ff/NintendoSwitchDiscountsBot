package com.example.nintendoswitchdiscountsbot.parser;

import java.math.BigDecimal;
import java.util.Optional;

import com.example.nintendoswitchdiscountsbot.business.Game;
import com.example.nintendoswitchdiscountsbot.dto.GameDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class GameMapper {

    private final ObjectMapper objectMapper;

    public Optional<GameDto> tryToDto(String gameJson) {
        try {
            return Optional.of(objectMapper.readValue(gameJson, GameDto.class));
        } catch (Exception e) {
            log.error("Game was not mapped - {}", gameJson, e);
            return Optional.empty();
        }
    }

    public Game toDomain(GameDto dto) {
        return new Game(
                dto.name(),
                dto.country(),
                BigDecimal.valueOf(dto.actualPrice()),
                dto.priceWithoutDiscount().map(BigDecimal::valueOf),
                dto.discountPercent(),
                dto.priceValidUntil(),
                dto.priceWithoutDiscount().isPresent()
        );
    }
}
