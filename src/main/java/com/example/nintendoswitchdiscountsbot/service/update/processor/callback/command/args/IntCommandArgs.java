package com.example.nintendoswitchdiscountsbot.service.update.processor.callback.command.args;

import lombok.Builder;

public record IntCommandArgs(
        Integer integer
) implements CommandArgs {
    @Builder
    public IntCommandArgs {
    }
}
