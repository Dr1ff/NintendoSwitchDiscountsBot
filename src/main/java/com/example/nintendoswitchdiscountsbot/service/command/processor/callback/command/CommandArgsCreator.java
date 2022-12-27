package com.example.nintendoswitchdiscountsbot.service.command.processor.callback.command;

import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.service.command.processor.callback.command.CommandArgs;

import java.util.List;

public interface CommandArgsCreator {

    CommandArgs create(List<String> dtoArgs);

    List<String> toList(CommandArgs commandArgs);

    Command getCommand();
}
