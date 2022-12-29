package com.example.nintendoswitchdiscountsbot.business;

import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.service.update.processor.callback.command.args.CommandArgs;
import com.example.nintendoswitchdiscountsbot.service.update.processor.callback.subcommand.args.SubcommandArgs;
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
