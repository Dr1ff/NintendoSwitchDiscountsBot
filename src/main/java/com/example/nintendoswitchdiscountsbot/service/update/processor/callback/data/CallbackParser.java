package com.example.nintendoswitchdiscountsbot.service.update.processor.callback.data;

import com.example.nintendoswitchdiscountsbot.business.CallbackData;
import com.example.nintendoswitchdiscountsbot.dto.CallbackDto;
import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.service.update.processor.callback.command.args.CommandArgs;
import com.example.nintendoswitchdiscountsbot.service.update.processor.callback.command.args.CommandArgsCreator;
import com.example.nintendoswitchdiscountsbot.service.update.processor.callback.subcommand.args.SubcommandArgs;
import com.example.nintendoswitchdiscountsbot.service.update.processor.callback.subcommand.args.SubcommandArgsCreator;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CallbackParser {

    private final Map<Command, Map<Subcommand, SubcommandArgsCreator>> subcommandArgsCreators;
    private final Map<Command, CommandArgsCreator> commandArgsCreators;

    public CallbackParser(
            List<SubcommandArgsCreator> subcommandArgsCreators,
            List<CommandArgsCreator> commandArgsCreators
    ) {
        this.subcommandArgsCreators = new HashMap<>();
        subcommandArgsCreators.forEach(creator -> creator.getCommands().forEach(command -> {
            if (!this.subcommandArgsCreators.containsKey(command)) {
                this.subcommandArgsCreators.put(command, new HashMap<>());
            }
            creator.getSubcommands().forEach(subcommand ->
                    this.subcommandArgsCreators.get(command).put(subcommand, creator));
        }));
        this.commandArgsCreators = new HashMap<>();
        commandArgsCreators.forEach(creator -> creator.getCommands()
                .forEach(command -> this.commandArgsCreators.put(command, creator))
        );
    }

    public CallbackData fromDto(CallbackDto dto) { //todo: сделать что то с null
        CommandArgs commandArgs;
        SubcommandArgs subcommandArgs;
        if (Objects.isNull(dto.commandArgs())) {
            commandArgs = null;
        } else {
            commandArgs = commandArgsCreators.get(dto.command()).fromArgsList(dto.commandArgs());
        }
        if (Objects.isNull(dto.subcommandArgs())) {
            subcommandArgs = null;
        } else {
            subcommandArgs = subcommandArgsCreators
                    .get(dto.command())
                    .get(dto.subcommand())
                    .fromArgsList(dto.subcommandArgs());
        }
        return new CallbackData(
                dto.command(),
                Optional.ofNullable(dto.subcommand()),
                Optional.ofNullable(commandArgs),
                Optional.ofNullable(subcommandArgs)
        );
    }

    public CallbackDto fromData(CallbackData data) {

        Subcommand subcommand;
        List<String> commandArgs;
        List<String> subcommandArgs;

        if (data.subcommand().isEmpty()) {
            subcommand = null;
        } else {
            subcommand = data.subcommand().get();
        }
        if (data.commandArgs().isEmpty()) {
            commandArgs = null;
        } else {
            commandArgs = commandArgsCreators
                    .get(data.command())
                    .toArgsList(data.commandArgs().get());
        }
        if (data.subcommandArgs().isEmpty()) {
            subcommandArgs = null;
        } else {
            subcommandArgs = subcommandArgsCreators
                    .get(data.command())
                    .get(data.subcommand().get())
                    .toArgsList(data.subcommandArgs().get());
        }
        return new CallbackDto(
                data.command(),
                subcommand,
                commandArgs,
                subcommandArgs
        );
    }
}
