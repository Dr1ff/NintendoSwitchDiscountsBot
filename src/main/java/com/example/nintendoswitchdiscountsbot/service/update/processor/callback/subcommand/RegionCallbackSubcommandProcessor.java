package com.example.nintendoswitchdiscountsbot.service.update.processor.callback.subcommand;

import com.example.nintendoswitchdiscountsbot.business.CallbackData;
import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.enums.Country;
import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.service.keyboard.KeyboardService;
import com.example.nintendoswitchdiscountsbot.service.storage.UserStorageService;
import com.example.nintendoswitchdiscountsbot.service.update.messenger.region.RegionMessenger;
import com.example.nintendoswitchdiscountsbot.service.update.processor.callback.subcommand.args.country.CountrySubcommandArgs;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Component
public class RegionCallbackSubcommandProcessor implements CallbackSubcommandProcessor {

    private final Map<Subcommand, KeyboardService> keyboardServices;
    private final Map<Subcommand, RegionMessenger> messengers;
    private final UserStorageService userStorageService;

    public RegionCallbackSubcommandProcessor(
            List<KeyboardService> keyboardServices,
            List<RegionMessenger> messengers,
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
        var subcommand = getSubcommand(callbackData);
        if (subcommand.equals(Subcommand.ACCEPT)) {
            userStorageService.setCountryToUser(
                    getCountryFromArgs(callbackData),
                    callbackQuery.getFrom().getId()
            );
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
                Subcommand.ACCEPT,
                Subcommand.REGION
        );
    }

    @Override
    public Command getCommand() {
        return Command.REGION;
    }

    private Subcommand getSubcommand(CallbackData callbackData) {
        return callbackData.subcommand().orElseThrow(
                () -> new IllegalArgumentException(
                        "В RegisterCallbackSubcommandProcessor попала callbackData " +
                                "с subcommand = Optional.empty"
                )
        );
    }

    private Country getCountryFromArgs(CallbackData callbackData) {
        return ((CountrySubcommandArgs) callbackData.subcommandArgs()
                .orElseThrow(
                        () -> new IllegalArgumentException(
                                "В RegisterCallbackSubcommandProcessor попала callbackData " +
                                        "с subcommandArgs = Optional.empty"
                        )
                ))
                .country();
    }
}
