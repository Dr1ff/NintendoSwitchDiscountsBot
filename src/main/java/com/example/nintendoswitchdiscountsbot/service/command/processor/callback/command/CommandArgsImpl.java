package com.example.nintendoswitchdiscountsbot.service.command.processor.callback.command;

import com.example.nintendoswitchdiscountsbot.service.command.processor.callback.command.CommandArgs;
import lombok.Builder;

public record CommandArgsImpl(
        String commandArgs
) implements CommandArgs {
    @Builder
    public CommandArgsImpl {
    }
}
