package com.example.nintendoswitchdiscountsbot.service.update.processor.callback.subcommand.args;

import lombok.Builder;

public record IntSubcommandArgs(
        int i
) implements SubcommandArgs {
    @Builder
    public IntSubcommandArgs {
    }
}
