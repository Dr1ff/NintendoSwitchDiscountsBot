package com.example.nintendoswitchdiscountsbot.service.update.processor.callback.command;

import com.example.nintendoswitchdiscountsbot.business.CallbackData;
import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.service.update.processor.callback.subcommand.CallbackSubcommandProcessor;
import com.example.nintendoswitchdiscountsbot.service.utils.UserStateValidator;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AddGameCallbackCommandProcessor implements CallbackCommandProcessor {

    private final Map<Subcommand, CallbackSubcommandProcessor> subcommandProcessors;
    private final UserStateValidator userStateValidator;

    public AddGameCallbackCommandProcessor(
            List<CallbackSubcommandProcessor> processors,
            UserStateValidator userStateValidator
    ) {
        this.userStateValidator = userStateValidator;
        Map<Subcommand, CallbackSubcommandProcessor> map = new HashMap<>();
        processors.stream()
                .filter(processor ->
                        processor.getCommand().equals(getCommand()))
                .forEach(processor ->
                        processor.getSubcommands().forEach(subcommand -> map.put(subcommand, processor)));
        this.subcommandProcessors = map;
    }

    @Override
    public void process(CallbackQuery callbackQuery, CallbackData callbackData) {
        userStateValidator.validation(
                callbackQuery.getMessage().getChatId(),
                this
        );
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
        return Command.G_ADD;
    }
}
