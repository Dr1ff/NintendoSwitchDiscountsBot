package com.example.nintendoswitchdiscountsbot.service.update.processor.callback.command;

import com.example.nintendoswitchdiscountsbot.business.CallbackData;
import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.service.storage.UserStorageService;
import com.example.nintendoswitchdiscountsbot.service.keyboard.menu.MenuKeyboardService;
import com.example.nintendoswitchdiscountsbot.service.update.messenger.menu.MenuMessenger;
import com.example.nintendoswitchdiscountsbot.service.utils.UserStateValidator;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MenuCallbackCommandProcessor implements CallbackCommandProcessor {

    private final MenuKeyboardService keyboardService;
    private final Map<Subcommand, MenuMessenger> replies;
    private final UserStorageService userStorageService;
    private final UserStateValidator userStateValidator;

    public MenuCallbackCommandProcessor(
            MenuKeyboardService keyboardService,
            List<MenuMessenger> replies,
            UserStorageService userStorageService,
            UserStateValidator userStateValidator
    ) {
        this.keyboardService = keyboardService;
        this.userStorageService = userStorageService;
        this.userStateValidator = userStateValidator;
        Map<Subcommand, MenuMessenger> replyMap = new HashMap<>();
        replies.forEach(reply -> reply.getSubcommand()
                .forEach(subcommand -> replyMap.put(subcommand, reply)));
        this.replies = replyMap;
    }

    @Override
    public void process(CallbackQuery callbackQuery, CallbackData callbackData) {
        userStateValidator.validation(
                callbackQuery.getMessage().getChatId(),
                this
        );
        userStorageService.add(
                userStorageService
                        .findById(callbackQuery.getFrom().getId())
                        .orElseThrow(
                                () -> new IllegalArgumentException(
                                        "В MenuCallbackCommandProcessor CallbackQuery " +
                                                "с chatId пользователя незаписанного в бд"
                                )
                        )
                        .toBuilder()
                        .state(Command.MENU)
                        .build()
        );
        replies.get(
                        callbackData.subcommand().orElseThrow(
                                () -> new IllegalArgumentException(
                                        "В MenuCallbackCommandProcessor попала callbackData " +
                                                "с subcommand = Optional.empty"
                                )
                        )
                )
                .reply(
                        callbackQuery,
                        keyboardService.getMarkup(callbackData)
                );
    }

    @Override
    public Command getCommand() {
        return Command.MENU;
    }
}