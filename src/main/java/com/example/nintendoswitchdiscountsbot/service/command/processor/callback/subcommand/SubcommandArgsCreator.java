package com.example.nintendoswitchdiscountsbot.service.command.processor.callback.subcommand;

import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.enums.Subcommand;

import java.util.List;
import java.util.Set;

public interface SubcommandArgsCreator {

    SubcommandArgs fromArgsList(List<String> dtoArgs);

    List<String> toArgsList(SubcommandArgs subcommandArgs);

    Set<Subcommand> getSubcommands();

    Command getCommand();
}
