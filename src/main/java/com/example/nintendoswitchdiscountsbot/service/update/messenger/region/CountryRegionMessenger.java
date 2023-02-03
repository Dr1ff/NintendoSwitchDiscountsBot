package com.example.nintendoswitchdiscountsbot.service.update.messenger.region;

import com.example.nintendoswitchdiscountsbot.business.CallbackData;
import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.service.observer.MessageEventPublisher;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Component
@RequiredArgsConstructor
public class CountryRegionMessenger extends RegionMessenger {

    private final static String PARSE_MODE = "html";

    private final MessageEventPublisher messageEventPublisher;

    @Override
    public Set<Subcommand> getSubcommand() {
        return Set.of(
                Subcommand.NEXT,
                Subcommand.PREV,
                Subcommand.CANCEL,
                Subcommand.REGION
        );
    }

    @Override
    public void reply(CallbackQuery callbackQuery, CallbackData callbackData, InlineKeyboardMarkup replyMarkup) {
        messageEventPublisher.publish(
                EditMessageText.builder()
                        .text("<b>Выберите регион</b>" +
                                "\n⚠️<u>Обратите внимание</u>, если игры из вашего списка не " +
                                "представлены в магазине для нового выбранного региона," +
                                "они будут автоматически <b><u>удалены</u></b> из вишлиста")
                        .parseMode(PARSE_MODE)
                        .messageId(callbackQuery.getMessage().getMessageId())
                        .chatId(callbackQuery.getMessage().getChatId())
                        .replyMarkup(replyMarkup)
                        .build()
        );
    }
}
