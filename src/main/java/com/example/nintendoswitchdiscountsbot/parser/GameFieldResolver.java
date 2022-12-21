package com.example.nintendoswitchdiscountsbot.parser;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Optional;

import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author Alexander Popov
 */
@Component
public class GameFieldResolver {

    public String stringField(Elements elements) {
        return elements.text();
    }

    public double doubleField(Elements elements) {
        return Double.parseDouble(extractNumber(elements.text()));
    }

    public Optional<Double> doubleFieldO(Elements elements) {
        String rawNumber = extractNumber(elements.text());
        return StringUtils.hasText(rawNumber) ? Optional.of(Double.parseDouble(rawNumber)) : Optional.empty();
    }

    public Optional<Instant> instantFieldO(Elements elements) {
        String rawDate = elements.text();
        if (StringUtils.hasText(rawDate)) {
            var date = LocalDate.parse(rawDate).atTime(LocalTime.MIDNIGHT).atZone(ZoneOffset.UTC).toInstant();
            return Optional.of(date);
        }
        return Optional.empty();
    }

    private String extractNumber(String element) {
        return element.replaceAll("[^0-9.]+", "");
    }
}
