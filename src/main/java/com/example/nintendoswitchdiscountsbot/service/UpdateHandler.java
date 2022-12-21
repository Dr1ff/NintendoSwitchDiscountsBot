package com.example.nintendoswitchdiscountsbot.service;

import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.service.command.CallbackProcessor;
import com.example.nintendoswitchdiscountsbot.service.command.CommandProcessor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class UpdateHandler {
    private final Map<Command, CommandProcessor> commandProcessors;
    private final CallbackProcessor callbackProcessor;

    public UpdateHandler(List<CommandProcessor> processors, CallbackProcessor callbackProcessor) {
        this.commandProcessors = processors
                .stream()
                .collect(Collectors.toMap(CommandProcessor::getCommand, Function.identity()));
        this.callbackProcessor = callbackProcessor;
    }


    public void processing(Update update) {
       if (update.hasMessage() && update.getMessage().hasText()) {
           String messageText = update.getMessage().getText().trim().replace("/", "").toUpperCase();
           Optional<Command> commandO = Optional.of(Command.valueOf(messageText));
           commandO.ifPresent(command -> commandProcessors.get(command).process(update));
       } else if(update.hasCallbackQuery()) {
           callbackProcessor.process(update.getCallbackQuery());
       }
    }
}
