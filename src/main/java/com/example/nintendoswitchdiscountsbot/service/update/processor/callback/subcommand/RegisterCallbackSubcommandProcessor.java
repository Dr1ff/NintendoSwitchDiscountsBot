package com.example.nintendoswitchdiscountsbot.service.update.processor.callback.subcommand;

import com.example.nintendoswitchdiscountsbot.business.CallbackData;
import com.example.nintendoswitchdiscountsbot.business.User;
import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.service.keyboard.KeyboardService;
import com.example.nintendoswitchdiscountsbot.service.storage.UserStorageService;
import com.example.nintendoswitchdiscountsbot.service.update.messenger.register.RegisterMessenger;
import com.example.nintendoswitchdiscountsbot.service.update.processor.callback.subcommand.args.country.CountrySubcommandArgs;
import java.util.ArrayList;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class RegisterCallbackSubcommandProcessor implements CallbackSubcommandProcessor {

    private final Map<Subcommand, KeyboardService> keyboardServices;
    private final Map<Subcommand, RegisterMessenger> messengers;
    private final UserStorageService userStorageService;

    public RegisterCallbackSubcommandProcessor(
            List<KeyboardService> keyboardServices,
            List<RegisterMessenger> messengers,
            UserStorageService userStorageService
    ) {
        this.userStorageService = userStorageService;
        this.keyboardServices = new HashMap<>();
        keyboardServices
                .stream()
                .filter(service -> service.getCommands().contains(getCommand()))
                .forEach(keyboardService ->
                        keyboardService.getSubcommands()
                                .forEach(subcommand ->
                                        this.keyboardServices.put(subcommand, keyboardService)
                                )
                );
        this.messengers = new HashMap<>();
        messengers
                .forEach(messenger ->
                        messenger.getSubcommand()
                                .forEach(subcommand ->
                                        this.messengers.put(subcommand, messenger)
                                )
                );
    }

    @Override
    public void process(CallbackQuery callbackQuery, CallbackData callbackData) {
        var subcommand = callbackData.subcommand().orElseThrow(
                () -> new IllegalArgumentException(
                        "В RegisterCallbackSubcommandProcessor попала callbackData " +
                                "с subcommand = Optional.empty"
                )
        );
        if (subcommand.equals(Subcommand.ACCEPT)) {
            userStorageService.add(new User(
                    callbackQuery.getFrom().getId(),
                    new ArrayList<>(),
                    ((CountrySubcommandArgs) callbackData.subcommandArgs()
                            .orElseThrow(
                                    () -> new IllegalArgumentException(
                                            "В RegisterCallbackSubcommandProcessor попала callbackData " +
                                                    "с subcommandArgs = Optional.empty"
                                    )
                            )
                    ).country(),
                    getCommand()
            ));
        }
        messengers
                .get(subcommand)
                .reply(
                        callbackQuery,
                        callbackData,
                        keyboardServices
                                .get(subcommand)
                                .getMarkup(callbackData)
                );
    }

    @Override
    public Set<Subcommand> getSubcommands() {
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
