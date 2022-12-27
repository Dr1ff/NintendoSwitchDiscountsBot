package com.example.nintendoswitchdiscountsbot.service.command.processor.callback;

import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.service.command.processor.callback.command.CommandArgs;
import com.example.nintendoswitchdiscountsbot.service.command.processor.callback.subcommand.SubcommandArgs;
import lombok.Builder;

public record CallbackData(
        Command command,
        Subcommand subcommand,
        CommandArgs commandArgs,
        SubcommandArgs subcommandArgs
) {
    @Builder(toBuilder = true)
    public CallbackData {
    }
}
