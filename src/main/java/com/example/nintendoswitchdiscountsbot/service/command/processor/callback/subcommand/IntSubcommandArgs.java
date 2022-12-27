package com.example.nintendoswitchdiscountsbot.service.command.processor.callback.subcommand;

import com.example.nintendoswitchdiscountsbot.service.command.processor.callback.subcommand.SubcommandArgs;
import lombok.Builder;

public record IntSubcommandArgs(
        int firstButtonIndex
) implements SubcommandArgs {
    @Builder
    public IntSubcommandArgs {
    }
}
