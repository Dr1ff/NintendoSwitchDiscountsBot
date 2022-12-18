package com.example.nintendoswitchdiscountsbot.parser;

import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Parser {

    @SneakyThrows
    public void parse() {

        String pathTemplate = "https://ntdeals.net/ru-store/all-games/%s?type=games";
        Document document;
        int pageNum = 1;
        do {
            document = Jsoup.connect(String.format(pathTemplate, pageNum++)).get();
            Elements gamesDetails = document.getElementsByAttributeValueEnding("class", "game-collection-item-details");
            // some logic
            getGames(gamesDetails);
        } while (!isLastPage(document));
        System.out.println(1);
    }

    private boolean isLastPage(Document page) {
        var pagination = page.getElementsByAttributeValue("class", "pagination");
        return pagination.stream().anyMatch(paginationElement -> {
            return paginationElement.children().stream().anyMatch(paginationElementChild -> {
                return paginationElementChild.attributes().asList().stream().anyMatch(paginationElementChildAttribute -> {
                    return paginationElementChildAttribute.getValue().equals("next disabled");
                });
            });
        });
    }

    private void getGames(Elements gamesDetails) {
        gamesDetails.forEach(gameData -> {
            String name = gameData.attr("itemdrop", "name").wholeText();
            Optional<String> discount = Optional.of(gameData
                    .attr("class", "game-collection-item-discounts").wholeText());
            String price = gameData.attr("itemdrop", "offers").wholeText();
            Optional<String> priceWithDiscount = Optional.of(gameData.child(0)
                    .attr("class", "game-collection-item-price-discount").wholeText());
            System.out.println(name + " | " + price + " | " + discount + " | " + priceWithDiscount );
        });
    }


}
