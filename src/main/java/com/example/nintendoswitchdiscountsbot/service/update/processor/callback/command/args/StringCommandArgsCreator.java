package com.example.nintendoswitchdiscountsbot.service.update.processor.callback.command.args;

import com.example.nintendoswitchdiscountsbot.enums.Command;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class StringCommandArgsCreator implements CommandArgsCreator {

    private final static int FIRST_ARG_INDEX = 0;

    @Override
    public CommandArgs fromArgsList(List<String> dtoArgs) {
        return new StringCommandArgs(dtoArgs.get(FIRST_ARG_INDEX));
    }

    @Override
    public List<String> toArgsList(CommandArgs commandArgs) {
       return List.of(((StringCommandArgs) commandArgs).string());
    }

    @Override
    public Set<Command> getCommands() {
        return Set.of(Command.REGISTER, Command.BREAK);
    }

}
