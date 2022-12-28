package com.example.nintendoswitchdiscountsbot.service.command.processor.callback.subcommand;

import lombok.Builder;

public record IntSubcommandArgs(
        int i
) implements SubcommandArgs {
    @Builder
    public IntSubcommandArgs {
    }
}
