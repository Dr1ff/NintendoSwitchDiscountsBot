package com.example.nintendoswitchdiscountsbot.service.command.processor.callback.subcommand;

import com.example.nintendoswitchdiscountsbot.enums.Country;
import com.example.nintendoswitchdiscountsbot.service.command.processor.callback.subcommand.SubcommandArgs;
import lombok.Builder;

public record CountrySubcommandArgs(
        Country country
) implements SubcommandArgs {
    @Builder
    public CountrySubcommandArgs {
    }
}
