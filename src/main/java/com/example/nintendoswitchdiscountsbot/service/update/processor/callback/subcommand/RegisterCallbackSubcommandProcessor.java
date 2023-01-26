package com.example.nintendoswitchdiscountsbot.service.update.processor.callback.subcommand;

import com.example.nintendoswitchdiscountsbot.business.CallbackData;
import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.service.update.keyboard.KeyboardService;
import com.example.nintendoswitchdiscountsbot.service.update.reply.register.RegisterMessenger;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class RegisterCallbackSubcommandProcessor implements CallbackSubcommandProcessor {

    private final Map<Subcommand, KeyboardService> keyboardServices;
    private final Map<Subcommand, RegisterMessenger> replyMap;

    public RegisterCallbackSubcommandProcessor(
            List<KeyboardService> keyboardServices,
            List<RegisterMessenger> replies
    ) {
        Map<Subcommand, KeyboardService> keyboardServiceMap = new HashMap<>();
        keyboardServices
                .stream()
                .filter(service -> service.getCommands().contains(getCommand()))
                .forEach(keyboardService ->
                        keyboardService.getSubcommands()
                                .forEach(subcommand ->
                                        keyboardServiceMap.put(subcommand, keyboardService)));

        Map<Subcommand, RegisterMessenger> repliesMap = new HashMap<>();
        replies
                .forEach(reply -> reply.getSubcommand()
                        .forEach(subcommand -> repliesMap.put(subcommand, reply)));
        this.keyboardServices = keyboardServiceMap;
        this.replyMap = repliesMap;
    }

    @Override
    public void process(CallbackQuery callbackQuery, CallbackData callbackData) {
        replyMap.get(
                        callbackData.subcommand().orElseThrow(
                                () -> new IllegalArgumentException(
                                        "В RegisterCallbackSubcommandProcessor попала callbackData " +
                                                "с subcommand = Optional.empty"
                                )
                        )
                )
                .reply(
                        callbackQuery,
                        callbackData,
                        keyboardServices.get(callbackData.subcommand().get())
                                .getMarkup(callbackData)
                );
    }

    @Override
    public Set<Subcommand> getSubcommand() {
        return Set.of(
                Subcommand.PREV,
                Subcommand.NEXT,
                Subcommand.CONFIRM,
                Subcommand.CANCEL,
                Subcommand.ACCEPT
        );
    }

    @Override
    public Command getCommand() {
        return Command.REGISTER;
    }
}
