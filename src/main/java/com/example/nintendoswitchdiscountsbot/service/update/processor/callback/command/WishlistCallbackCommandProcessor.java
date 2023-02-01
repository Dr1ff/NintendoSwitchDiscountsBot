package com.example.nintendoswitchdiscountsbot.service.update.processor.callback.command;

import com.example.nintendoswitchdiscountsbot.business.CallbackData;
import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.service.update.processor.callback.subcommand.CallbackSubcommandProcessor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Component
public class WishlistCallbackCommandProcessor implements CallbackCommandProcessor  {

    private final Map<Subcommand, CallbackSubcommandProcessor> subcommandProcessors;

    public WishlistCallbackCommandProcessor(List<CallbackSubcommandProcessor> subcommandProcessors) {
        Map<Subcommand, CallbackSubcommandProcessor> map = new HashMap<>();
        subcommandProcessors.stream()
                .filter(processor ->
                        processor.getCommand().equals(getCommand()))
                .forEach(processor ->
                        processor.getSubcommands().forEach(subcommand -> map.put(subcommand, processor)));
        this.subcommandProcessors = map;
    }

    @Override
    public void process(CallbackQuery callbackQuery, CallbackData callbackData) {
        subcommandProcessors
                .get(
                        callbackData.subcommand().orElseThrow(() -> new IllegalArgumentException(
                                        "AddGameCallbackCommandProcessor получил callbackData" +
                                                "с subcommand = Optional.empty"
                                )
                        )
                )
                .process(callbackQuery, callbackData);
    }

    @Override
    public Command getCommand() {
        return Command.WISHLIST;
    }
}
