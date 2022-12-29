package com.example.nintendoswitchdiscountsbot.service.update.processor.callback.command.args;

import com.example.nintendoswitchdiscountsbot.enums.Command;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class CommandArgsCreatorImpl implements CommandArgsCreator {

    @Override
    public CommandArgs fromArgsList(List<String> dtoArgs) {
        return new CommandArgsImpl(dtoArgs.get(0));
    }

    @Override
    public List<String> toArgsList(CommandArgs commandArgs) {
       return List.of(((CommandArgsImpl) commandArgs).commandArgs());
    }

    @Override
    public Set<Command> getCommands() {
        return Set.of(Command.REGISTER, Command.BREAK);
    }

}
