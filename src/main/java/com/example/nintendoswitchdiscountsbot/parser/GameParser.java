package com.example.nintendoswitchdiscountsbot.parser;

import com.example.nintendoswitchdiscountsbot.dto.GameDto;
import com.example.nintendoswitchdiscountsbot.enums.Country;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class GameParser {

    private final GameMapper gameMapper;
    private final ObjectMapper objectMapper;
    private final GameFieldResolver gameFieldResolver;

    public List<GameDto> parse(Document gamesListPage, Country country) {
        var gamesDetails = gamesListPage.getElementsByAttributeValueEnding(
                "class", "game-collection-item-details"
        );
        return gamesDetails.stream()
                .map(e -> tryParseGameDetailsToJson(e, country))
                .filter(Optional::isPresent)
                .map(e -> gameMapper.tryToDto(e.get()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    public boolean isLastPage(Document page) {
        return !page.getElementsByClass("next disabled").isEmpty();
    }

    private Optional<String> tryParseGameDetailsToJson(Element gameDetails, Country country) {
        try {
            return Optional.of(parseGameDetailsToJson(gameDetails, country));
        } catch (Exception e) {
            log.error("Game was not parsed - {}", gameDetails, e);
            return Optional.empty();
        }
    }

    @SneakyThrows
    private String parseGameDetailsToJson(Element gameDetails, Country country) {
        Map<String, Object> gameDetailsMap = new HashMap<>();
        gameDetailsMap.put(
                "name",
                gameFieldResolver.stringField(gameDetails.getElementsByAttributeValue("itemprop", "name"))
        );
        gameDetailsMap.put(
                "actualPrice",
                gameFieldResolver.doubleField(gameDetails.getElementsByAttributeValue("itemprop", "price"))
        );
        gameDetailsMap.put(
                "discountPercent",
                gameFieldResolver.doubleFieldO(
                        gameDetails.getElementsByAttributeValue("class", "game-collection-item-discount"))
        );
        gameDetailsMap.put(
                "priceValidUntil",
                gameFieldResolver.instantFieldO(gameDetails.getElementsByAttributeValue("itemprop", "priceValidUntil"))
        );
        gameDetailsMap.put(
                "priceWithoutDiscount",
                gameFieldResolver.doubleFieldO(
                        gameDetails.getElementsByAttributeValue("class", "game-collection-item-price strikethrough"))
        );
        gameDetailsMap.put("country", country);
        return objectMapper.writeValueAsString(gameDetailsMap);
    }
}
