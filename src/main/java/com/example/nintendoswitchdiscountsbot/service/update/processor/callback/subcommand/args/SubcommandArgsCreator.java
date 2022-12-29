package com.example.nintendoswitchdiscountsbot.service.update.processor.callback.subcommand.args;

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
