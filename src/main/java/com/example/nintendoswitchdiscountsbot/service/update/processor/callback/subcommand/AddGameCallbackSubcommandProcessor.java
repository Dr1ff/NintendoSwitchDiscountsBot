package com.example.nintendoswitchdiscountsbot.service.update.processor.callback.subcommand;

import com.example.nintendoswitchdiscountsbot.business.CallbackData;
import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.service.update.keyboard.KeyboardService;
import com.example.nintendoswitchdiscountsbot.service.update.reply.add_game.AddGameMessenger;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class AddGameCallbackSubcommandProcessor implements CallbackSubcommandProcessor {

    private final Map<Subcommand, AddGameMessenger> messengers;
    private final Map<Subcommand, KeyboardService> keyboardServices;

    public AddGameCallbackSubcommandProcessor(
            List<AddGameMessenger> messengers,
            List<KeyboardService> keyboardServices
    ) {
        this.keyboardServices = new HashMap<>();
        keyboardServices
                .stream()
                .filter(service -> service.getCommands().contains(getCommand()))
                .forEach(keyboardService -> keyboardService.getSubcommands()
                        .forEach(subcommand ->
                                this.keyboardServices.put(subcommand, keyboardService)));
        Map<Subcommand, AddGameMessenger> replyMap = new HashMap<>();
        messengers.forEach(reply -> reply.getSubcommand()
                .forEach(subcommand -> replyMap.put(subcommand, reply)));
        this.messengers = replyMap;
    }

    @Override
    public void process(CallbackQuery callbackQuery, CallbackData callbackData) {
        var subcommand = callbackData.subcommand().orElseThrow(
                () -> new IllegalArgumentException(
                        "В AddGameCallbackSubcommandProcessor попала callbackData " +
                                "с subcommand = Optional.empty"
                )
        );
        messengers.get(subcommand).reply(
                callbackQuery,
                callbackData,
                keyboardServices
                        .get(subcommand)
                        .getMarkup(callbackData)
        );
    }

    @Override
    public Set<Subcommand> getSubcommand() {
        return Set.of(
                Subcommand.ADD_GAME,
                Subcommand.CANCEL,
                Subcommand.ACCEPT,
                Subcommand.NEXT,
                Subcommand.PREV
        );
    }

    @Override
    public Command getCommand() {
        return Command.ADD_GAME;
    }
}
