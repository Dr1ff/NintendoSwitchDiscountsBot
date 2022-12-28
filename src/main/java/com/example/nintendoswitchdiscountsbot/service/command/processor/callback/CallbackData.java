package com.example.nintendoswitchdiscountsbot.service.command.processor.callback;

import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.service.command.processor.callback.command.CommandArgs;
import com.example.nintendoswitchdiscountsbot.service.command.processor.callback.subcommand.SubcommandArgs;
import lombok.Builder;

import java.util.Optional;

public record CallbackData(
        Command command,
        Optional<Subcommand> subcommand,
        Optional<CommandArgs> commandArgs,
        Optional<SubcommandArgs> subcommandArgs
) {
    @Builder(toBuilder = true)
    public CallbackData {
    }
}
