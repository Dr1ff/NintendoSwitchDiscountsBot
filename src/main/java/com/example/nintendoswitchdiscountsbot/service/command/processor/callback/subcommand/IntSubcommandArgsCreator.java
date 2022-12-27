package com.example.nintendoswitchdiscountsbot.service.command.processor.callback.subcommand;

import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class IntSubcommandArgsCreator implements SubcommandArgsCreator {
    @Override
    public SubcommandArgs create(List<String> dtoArgs) {
        return IntSubcommandArgs.builder()
                .firstButtonIndex(Integer.parseInt(dtoArgs.get(0)))
                .build();
    }

    @Override
    public List<String> toList(SubcommandArgs subcommandArgs) {
        return List.of(String.valueOf(((IntSubcommandArgs) subcommandArgs).firstButtonIndex()));
    }

    @Override
    public Set<Subcommand> getSubcommands() {
        return Set.of(
                Subcommand.NEXT,
                Subcommand.PREV,
                Subcommand.CANCEL
        );
    }

    @Override
    public Command getCommand() {
        return Command.REGISTER;
    }
}
