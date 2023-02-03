package com.example.nintendoswitchdiscountsbot.service.update.messenger.register;

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
public class AcceptRegisterMessenger extends RegisterMessenger {

    private final MessageEventPublisher messageEventPublisher;

    @Override
    public void reply(CallbackQuery callbackQuery, CallbackData callbackData, InlineKeyboardMarkup replyMarkup) {
        messageEventPublisher.publish(
                EditMessageText.builder()
                        .messageId(callbackQuery.getMessage().getMessageId())
                        .chatId(callbackQuery.getMessage().getChatId())
                        .text(getText(callbackData))
                        .replyMarkup(replyMarkup)
                        .build()
        );
    }

    @Override
    public Set<Subcommand> getSubcommand() {
        return Set.of(Subcommand.ACCEPT);
    }

    private Country getCountryFromData(CallbackData data) {
        return ((CountrySubcommandArgs) data.subcommandArgs().orElseThrow(
                () -> new IllegalArgumentException("В AcceptKeyboardService попала callbackData "
                        + "с subcommandArgs = Optional.empty")
        )).country();
    }

    private String getText(CallbackData data) {
        return String.format(
                "Регион %s успешно установлен! " +
                        "Цены на игры в боте будут указаны в валюте " +
                        "выбранного региона. Вы всегда можете изменить его в меню бота.",
                getCountryFromData(data) +
                        EmojiManager.getForAlias(
                                getCountryFromData(data)
                                        .name()
                                        .toLowerCase()
                        ).getUnicode()
        );
    }
}
