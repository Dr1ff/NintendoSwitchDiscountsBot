package com.example.nintendoswitchdiscountsbot.service.update.messenger.region;

import com.example.nintendoswitchdiscountsbot.business.CallbackData;
import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.service.observer.MessageEventPublisher;
import com.example.nintendoswitchdiscountsbot.service.update.processor.callback.subcommand.args.country.CountrySubcommandArgs;
import com.vdurmont.emoji.EmojiManager;
import java.util.Locale;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Component
@RequiredArgsConstructor
public class ConfirmRegionMessenger extends RegionMessenger {

    private final static String PARSE_MODE = "html";

    private final MessageEventPublisher messageEventPublisher;

    @Override
    public Set<Subcommand> getSubcommand() {
        return Set.of(Subcommand.CONFIRM);
    }

    @Override
    public void reply(CallbackQuery callbackQuery, CallbackData callbackData, InlineKeyboardMarkup replyMarkup) {
        messageEventPublisher.publish(
                EditMessageText.builder()
                        .messageId(callbackQuery.getMessage().getMessageId())
                        .chatId(callbackQuery.getMessage().getChatId())
                        .text(getConfirmText(callbackData))
                        .parseMode(PARSE_MODE)
                        .replyMarkup(replyMarkup)
                        .build()
        );
    }

    private String getConfirmText(CallbackData callbackData) {
        var country = (((CountrySubcommandArgs) callbackData.subcommandArgs().orElseThrow(
                () -> new IllegalArgumentException(
                        "В ConfirmRegisterReply попала callbackData " +
                                "с subcommandArgs = Optional.empty"
                )
        ))
                .country());
        return String.format(
                "Выбранный регион: <b>%s</b>. \nПодтвердите ваш выбор.",
                country + EmojiManager
                        .getForAlias(country.name().toLowerCase(Locale.ROOT))
                        .getUnicode()
        );
    }
}
