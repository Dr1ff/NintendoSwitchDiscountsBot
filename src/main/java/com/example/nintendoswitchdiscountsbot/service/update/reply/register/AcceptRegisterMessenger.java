package com.example.nintendoswitchdiscountsbot.service.update.reply.register;

import com.example.nintendoswitchdiscountsbot.business.CallbackData;
import com.example.nintendoswitchdiscountsbot.business.User;
import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.enums.Country;
import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.service.observer.MessageEventPublisher;
import com.example.nintendoswitchdiscountsbot.service.storage.UserStorageService;
import com.example.nintendoswitchdiscountsbot.service.update.processor.callback.subcommand.args.country.CountrySubcommandArgs;
import com.vdurmont.emoji.EmojiManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.ArrayList;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class AcceptRegisterMessenger extends RegisterMessenger {

    private final MessageEventPublisher messageEventPublisher;
    private final UserStorageService userStorageService;

    @Override
    public void reply(CallbackQuery callbackQuery, CallbackData callbackData, InlineKeyboardMarkup replyMarkup) {
        userStorageService.add(
                User.builder()
                        .id(callbackQuery.getFrom().getId())
                        .wishlist(new ArrayList<>())
                        .country(getCountryFromData(callbackData))
                        .state(Command.MENU)
                        .build()
        );
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
                """
                        Регион %s успешно установлен!
                        Цены на игры в боте будут указаны в валюте выбранного региона.
                        Вы всегда можете изменить его в меню бота.
                        Приступим к работе?
                        """,
                getCountryFromData(data) +
                        EmojiManager.getForAlias(
                                getCountryFromData(data)
                                        .name()
                                        .toLowerCase()
                        ).getUnicode()
        );
    }
}
