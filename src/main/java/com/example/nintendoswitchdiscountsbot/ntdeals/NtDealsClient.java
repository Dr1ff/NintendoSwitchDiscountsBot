package com.example.nintendoswitchdiscountsbot.ntdeals;

import java.util.Optional;

import com.example.nintendoswitchdiscountsbot.enums.Country;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Alexander Popov
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class NtDealsClient {

    @Value("${ntdeals.url.template.all-games}")
    private String allGamesUrl;
    @Value("${ntdeals.url.template.prices}")
    private String gamePricesUrl;
    @Value("${ntdeals.retry.timeout:3000}")
    private long retryTimeout;

    private final NtDealsOutgoingRpsLimiter rpsLimiter;

    public Document getGamesList(Country country, int pageNum) {
        var path = String.format(allGamesUrl, country, pageNum);
        return executeWithRepeatOrDie(Jsoup.connect(path));
    }

    public Document getGamePrices(String gameId) {
        var path = String.format(gamePricesUrl, gameId);
        var connection = Jsoup.connect(path)
                .ignoreContentType(true)
                .header("x-requested-with", "XMLHttpRequest");
        return executeWithRepeatOrDie(connection);
    }

    private Document executeWithRepeatOrDie(Connection connection) {
        var documentO = tryExecute(connection);
        if (documentO.isEmpty()) {
            log.error("The request to {} was not executed and will be repeated in {} seconds",
                    connection.request().url(), retryTimeout);
            rpsLimiter.sleep(retryTimeout);
            documentO = tryExecute(connection);
        }
        return documentO.orElseThrow(() ->
                new NtDealsClientException(String.format("Request to %s was not executed", connection.request().url())));
    }

    private Optional<Document> tryExecute(Connection connection) {
        try {
            rpsLimiter.acquire();
            return Optional.of(connection.get());
        } catch (Exception e) {
            log.error(e.getMessage());
            return Optional.empty();
        }
    }
}
