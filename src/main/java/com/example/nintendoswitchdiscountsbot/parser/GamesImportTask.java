package com.example.nintendoswitchdiscountsbot.parser;

import com.example.nintendoswitchdiscountsbot.enums.Country;
import com.example.nintendoswitchdiscountsbot.ntdeals.NtDealsClient;
import com.example.nintendoswitchdiscountsbot.service.storage.GameStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Alexander Popov
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class GamesImportTask {

    private final GameStorageService gameStorageService;
    private final NtDealsClient ntDealsClient;
    private final GameMapper gameMapper;
    private final GameParser gameParser;

    @Scheduled(cron = "${daily-tasks.start-time.games-import}")
    public void execute() {
        for (var country : Country.values()) {
            Document rawGamesList;
            var pageNum = 1;
            do {
                log.info("page {} will be processed", pageNum);
                rawGamesList = ntDealsClient.getGamesList(country, pageNum++);
                var gamesList = gameParser.parse(rawGamesList, country)
                        .stream()
                        .map(gameMapper::toDomain)
                        .toList();
                gameStorageService.addBatch(gamesList);
            } while (!gameParser.isLastPage(rawGamesList));
        }
    }
}
