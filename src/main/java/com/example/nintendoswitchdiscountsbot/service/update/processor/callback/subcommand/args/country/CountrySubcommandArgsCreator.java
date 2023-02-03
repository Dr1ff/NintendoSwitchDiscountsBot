package com.example.nintendoswitchdiscountsbot.service.update.processor.callback.subcommand.args.country;

import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.enums.Country;
import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.service.update.processor.callback.subcommand.args.SubcommandArgs;
import com.example.nintendoswitchdiscountsbot.service.update.processor.callback.subcommand.args.SubcommandArgsCreator;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class CountrySubcommandArgsCreator implements SubcommandArgsCreator {

    @Override
    public SubcommandArgs fromArgsList(List<String> dtoArgs) {
        return new CountrySubcommandArgs(Country.valueOf(dtoArgs.get(0)));
    }

    @Override
    public List<String> toArgsList(SubcommandArgs subcommandArgs) {
        return List.of(((CountrySubcommandArgs) subcommandArgs).country().name());
    }

    @Override
    public Set<Subcommand> getSubcommands() {
        return Set.of(Subcommand.CONFIRM, Subcommand.ACCEPT);
    }

    @Override
    public Set<Command> getCommands() {
        return Set.of(
                Command.REGISTER,
                Command.REGION
        );
    }

}
