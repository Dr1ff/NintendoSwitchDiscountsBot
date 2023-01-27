package com.example.nintendoswitchdiscountsbot.service.update.processor.callback.subcommand.args.integer;

import com.example.nintendoswitchdiscountsbot.service.update.processor.callback.subcommand.args.SubcommandArgs;
import lombok.Builder;

public record IntegerSubcommandArgs(
        Integer integer
) implements SubcommandArgs {
    @Builder
    public IntegerSubcommandArgs {
    }
}
