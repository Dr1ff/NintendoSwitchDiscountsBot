package com.example.nintendoswitchdiscountsbot.service;

import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.service.command.BotCommand;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class UpdateProcessor {
    private final Map<Command, BotCommand> commands;

    public UpdateProcessor( List<BotCommand> commands) {
        this.commands = new HashMap<Command, BotCommand>(commands
                .stream()
                .collect(Collectors.toMap(BotCommand::getCommand, Function.identity())));
    }


    public Optional<SendMessage> processing(Update update) {
       if (update.hasMessage() && update.getMessage().hasText()) {
           String messageText = update.getMessage().getText().trim().replace("/", "").toUpperCase();
           Optional<Command> commandO = Optional.of(Command.valueOf(messageText));
           return commandO.map(command -> commands.get(command).send(update));
       } else {
           return Optional.empty();
       }
    }
}
