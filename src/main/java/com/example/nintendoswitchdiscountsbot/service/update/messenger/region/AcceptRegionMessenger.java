package com.example.nintendoswitchdiscountsbot.service.update.messenger.region;

import com.example.nintendoswitchdiscountsbot.business.CallbackData;
import com.example.nintendoswitchdiscountsbot.enums.Country;
import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.service.observer.MessageEventPublisher;
import com.example.nintendoswitchdiscountsbot.service.update.processor.callback.subcommand.args.country.CountrySubcommandArgs;
import com.vdurmont.emoji.EmojiManager;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Component
@RequiredArgsConstructor
public class AcceptRegionMessenger extends RegionMessenger {

    private final static String PARSE_MODE = "html";

    private final MessageEventPublisher messageEventPublisher;

    @Override
    public void reply(CallbackQuery callbackQuery, CallbackData callbackData, InlineKeyboardMarkup replyMarkup) {
        messageEventPublisher.publish(
                EditMessageText.builder()
                        .text(String.format("Регион <b>%s</b> успешно установлен!",
                                        getCountryFromData(callbackData) +
                                                EmojiManager.getForAlias(
                                                        getCountryFromData(callbackData)
                                                                .name()
                                                                .toLowerCase()
                                                ).getUnicode()
                                )
                        )
                        .parseMode(PARSE_MODE)
                        .chatId(callbackQuery.getMessage().getChatId())
                        .messageId(callbackQuery.getMessage().getMessageId())
                        .replyMarkup(replyMarkup)
                        .build()
        );
    }

    @Override
    public Set<Subcommand> getSubcommand() {
        return Set.of(Subcommand.ACCEPT);
    }

    private Country getCountryFromData(CallbackData data) {
        return (
                (CountrySubcommandArgs) data.subcommandArgs()
                        .orElseThrow(
                                () -> new IllegalArgumentException("В AcceptKeyboardService попала callbackData "
                                        + "с subcommandArgs = Optional.empty")
                        )
        ).country();
    }
}
