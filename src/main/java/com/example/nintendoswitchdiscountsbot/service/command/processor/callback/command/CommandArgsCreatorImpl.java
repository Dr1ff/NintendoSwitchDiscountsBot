package com.example.nintendoswitchdiscountsbot.service.command.processor.callback.command;

import com.example.nintendoswitchdiscountsbot.enums.Command;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommandArgsCreatorImpl implements CommandArgsCreator {

    @Override
    public CommandArgs create(List<String> dtoArgs) {
        return CommandArgsImpl.builder()
                .commandArgs(dtoArgs.get(0))
                .build();
    }

    @Override
    public List<String> toList(CommandArgs commandArgs) {
       return List.of(((CommandArgsImpl) commandArgs).commandArgs());
    }

    @Override
    public Command getCommand() {
        return Command.REGISTER;
    }

}
