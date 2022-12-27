package com.example.nintendoswitchdiscountsbot.service.command.processor.callback;

import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.service.command.processor.callback.command.CommandArgsCreator;
import com.example.nintendoswitchdiscountsbot.service.command.processor.callback.subcommand.SubcommandArgsCreator;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CallbackParser {

    private final Map<Command, Map<Subcommand, SubcommandArgsCreator>> subcommandArgsCreators;
    private final Map<Command, CommandArgsCreator> commandArgsCreators;

    public CallbackParser(
            List<SubcommandArgsCreator> subcommandArgsCreators,
            List<CommandArgsCreator> commandArgsCreators
    ) {
        this.subcommandArgsCreators = new HashMap<>();
        subcommandArgsCreators.forEach(creator -> {
            if (!this.subcommandArgsCreators.containsKey(creator.getCommand())) {
                this.subcommandArgsCreators.put(creator.getCommand(), new HashMap<>());
            }
            creator.getSubcommands().forEach(subcommand ->
                    this.subcommandArgsCreators.get(creator.getCommand()).put(subcommand, creator));
        });
        this.commandArgsCreators = new HashMap<>();
        commandArgsCreators.forEach(creator -> creator.getCommands()
                .forEach(command -> this.commandArgsCreators.put(command, creator))
        );
    }

    public CallbackData fromDto(CallbackDto dto) {
        var builder = new CallbackData.CallbackDataBuilder();
        if (dto.command() != null) {
            builder.command(dto.command());
        }
        if (dto.commandArgs() != null) {
            builder.commandArgs(commandArgsCreators
                    .get(dto.command())
                    .create(dto.commandArgs())
            );
        }
        if (dto.subcommand() != null) {
            builder.subcommand(dto.subcommand());
        }
        if (dto.subcommandArgs() != null) {
            builder.subcommandArgs(subcommandArgsCreators
                    .get(dto.command())
                    .get(dto.subcommand())
                    .create(dto.subcommandArgs())
            );
        }
        return builder.build();
    }

    public CallbackDto fromData(CallbackData data) {
        var builder = new CallbackDto.CallbackDtoBuilder();
        if (data.command() != null) {
            builder.command(data.command());
        }
        if (data.commandArgs() != null) {
            builder.commandArgs(commandArgsCreators
                    .get(data.command())
                    .toList(data.commandArgs())
            );
        }
        if (data.subcommand() != null) {
            builder.subcommand(data.subcommand());
        }
        if (data.subcommandArgs() != null) {
            builder.subcommandArgs(subcommandArgsCreators
                    .get(data.command())
                    .get(data.subcommand())
                    .toList(data.subcommandArgs())
            );
        }
        return builder.build();
    }
}
