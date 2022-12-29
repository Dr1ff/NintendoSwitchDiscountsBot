package com.example.nintendoswitchdiscountsbot.service.update.processor.callback.command.args;

import lombok.Builder;

public record CommandArgsImpl(
        String commandArgs
) implements CommandArgs {
    @Builder
    public CommandArgsImpl {
    }
}
