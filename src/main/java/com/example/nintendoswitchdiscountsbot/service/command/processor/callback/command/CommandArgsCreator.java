package com.example.nintendoswitchdiscountsbot.service.command.processor.callback.command;

import com.example.nintendoswitchdiscountsbot.enums.Command;

import java.util.List;
import java.util.Set;

public interface CommandArgsCreator {

    CommandArgs fromArgsList(List<String> dtoArgs);

    List<String> toArgsList(CommandArgs commandArgs);

    Set<Command> getCommands();
}
