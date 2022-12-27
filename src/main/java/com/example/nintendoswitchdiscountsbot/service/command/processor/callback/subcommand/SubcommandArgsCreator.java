package com.example.nintendoswitchdiscountsbot.service.command.processor.callback.subcommand;

import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.service.command.processor.callback.subcommand.SubcommandArgs;

import java.util.List;
import java.util.Set;

public interface SubcommandArgsCreator {

    SubcommandArgs create(List<String> dtoArgs);

    List<String> toList(SubcommandArgs subcommandArgs);

    Set<Subcommand> getSubcommands();

    Command getCommand();
}
