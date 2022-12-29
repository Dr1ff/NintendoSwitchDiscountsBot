package com.example.nintendoswitchdiscountsbot.service.update.processor.callback.subcommand.args;

import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class IntSubcommandArgsCreator implements SubcommandArgsCreator {

    @Override
    public SubcommandArgs fromArgsList(List<String> dtoArgs) {
        return new IntSubcommandArgs(Integer.parseInt(dtoArgs.get(0)));
    }

    @Override
    public List<String> toArgsList(SubcommandArgs subcommandArgs) {
        return List.of(String.valueOf(((IntSubcommandArgs) subcommandArgs).i()));
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
