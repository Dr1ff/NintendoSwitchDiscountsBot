package com.example.nintendoswitchdiscountsbot.service.command.processor.callback.command;

import com.example.nintendoswitchdiscountsbot.enums.Command;

import java.util.List;
import java.util.Set;

public interface CommandArgsCreator {

    CommandArgs create(List<String> dtoArgs);

    List<String> toList(CommandArgs commandArgs);

    Set<Command> getCommands();
}
