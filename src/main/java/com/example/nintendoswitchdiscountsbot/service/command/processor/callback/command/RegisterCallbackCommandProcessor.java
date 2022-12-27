package com.example.nintendoswitchdiscountsbot.service.command.processor.callback.command;

import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.service.command.processor.callback.CallbackData;
import com.example.nintendoswitchdiscountsbot.service.command.processor.callback.subcommand.CallbackSubcommandProcessor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RegisterCallbackCommandProcessor implements CallbackCommandProcessor {

    private final Map<Subcommand, CallbackSubcommandProcessor> processors;

    public RegisterCallbackCommandProcessor(List<CallbackSubcommandProcessor> processors) {
        Map<Subcommand, CallbackSubcommandProcessor> map = new HashMap<>();
        processors.forEach(processor ->
                processor.getSubcommand().forEach(subcommand -> map.put(subcommand, processor))
        );
        this.processors = map;
    }

    public void process(CallbackQuery callbackQuery, CallbackData callbackData) {
         processors.get(callbackData.subcommand()).process(callbackQuery, callbackData);
    }

    public Command getCommand() {
        return Command.REGISTER;
    }
}
