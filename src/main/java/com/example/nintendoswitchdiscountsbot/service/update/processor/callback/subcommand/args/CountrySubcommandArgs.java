package com.example.nintendoswitchdiscountsbot.service.update.processor.callback.subcommand.args;

import com.example.nintendoswitchdiscountsbot.enums.Country;
import lombok.Builder;

public record CountrySubcommandArgs(
        Country country
) implements SubcommandArgs {
    @Builder
    public CountrySubcommandArgs {
    }
}
