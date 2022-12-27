package com.example.nintendoswitchdiscountsbot.service.command.processor.callback;

import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.service.command.processor.callback.command.CommandArgsCreator;
import com.example.nintendoswitchdiscountsbot.service.command.processor.callback.subcommand.SubcommandArgsCreator;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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
        this.commandArgsCreators = commandArgsCreators
                .stream()
                .collect(Collectors.toMap(CommandArgsCreator::getCommand, Function.identity()));
    }

    public CallbackData fromDto(CallbackDto dto) {
        return CallbackData.builder()
                .command(dto.command())
                .commandArgs(
                        commandArgsCreators.get(dto.command())
                                .create(dto.commandArgs())
                )
                .subcommand(dto.subcommand())
                .subcommandArgs(
                        subcommandArgsCreators
                                .get(dto.command())
                                .get(dto.subcommand())
                                .create(dto.subcommandArgs())
                )
                .build();
    }

    public CallbackDto fromData(CallbackData data) {
        return CallbackDto.builder()
                .command(data.command())
                .commandArgs(commandArgsCreators
                        .get(data.command())
                        .toList(data.commandArgs())
                )
                .subcommand(data.subcommand())
                .subcommandArgs(subcommandArgsCreators
                        .get(data.command())
                        .get(data.subcommand())
                        .toList(data.subcommandArgs())
                )
                .build();
    }
}
