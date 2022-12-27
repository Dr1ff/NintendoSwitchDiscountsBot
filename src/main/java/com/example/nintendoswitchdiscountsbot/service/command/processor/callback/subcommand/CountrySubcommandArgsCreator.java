package com.example.nintendoswitchdiscountsbot.service.command.processor.callback.subcommand;

import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.enums.Country;
import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class CountrySubcommandArgsCreator implements SubcommandArgsCreator {

    @Override
    public SubcommandArgs create(List<String> dtoArgs) {
        return CountrySubcommandArgs.builder()
                .country(Country.valueOf(dtoArgs.get(0)))
                .build();
    }

    @Override
    public List<String> toList(SubcommandArgs subcommandArgs) {
        return List.of(((CountrySubcommandArgs) subcommandArgs).country().name());
    }

    @Override
    public Set<Subcommand> getSubcommands() {
        return Set.of(Subcommand.CONFIRM, Subcommand.ACCEPT);
    }

    @Override
    public Command getCommand() {
        return Command.REGISTER;
    }

}
