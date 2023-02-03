package com.example.nintendoswitchdiscountsbot.service.update.processor.callback.command.args;

import lombok.Builder;

public record StringCommandArgs(
        String string
) implements CommandArgs {
    @Builder
    public StringCommandArgs {
    }
}
