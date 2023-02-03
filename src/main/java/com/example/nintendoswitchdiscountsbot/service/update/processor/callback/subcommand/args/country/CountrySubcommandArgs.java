package com.example.nintendoswitchdiscountsbot.service.update.processor.callback.subcommand.args.country;

import com.example.nintendoswitchdiscountsbot.enums.Country;
import com.example.nintendoswitchdiscountsbot.service.update.processor.callback.subcommand.args.SubcommandArgs;
import lombok.Builder;

public record CountrySubcommandArgs(
        Country country
) implements SubcommandArgs {
    @Builder
    public CountrySubcommandArgs {
    }
}
