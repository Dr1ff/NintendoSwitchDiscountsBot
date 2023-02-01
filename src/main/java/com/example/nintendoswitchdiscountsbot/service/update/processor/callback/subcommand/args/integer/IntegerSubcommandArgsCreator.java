package com.example.nintendoswitchdiscountsbot.service.update.processor.callback.subcommand.args.integer;

import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.service.update.processor.callback.subcommand.args.SubcommandArgs;
import com.example.nintendoswitchdiscountsbot.service.update.processor.callback.subcommand.args.SubcommandArgsCreator;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class IntegerSubcommandArgsCreator implements SubcommandArgsCreator {

    @Override
    public SubcommandArgs fromArgsList(List<String> dtoArgs) {
        return new IntegerSubcommandArgs(Integer.parseInt(dtoArgs.get(0)));
    }

    @Override
    public List<String> toArgsList(SubcommandArgs subcommandArgs) {
        return List.of(String.valueOf(((IntegerSubcommandArgs) subcommandArgs).integer()));
    }

    @Override
    public Set<Subcommand> getSubcommands() {
        return Set.of(
                Subcommand.NEXT,
                Subcommand.PREV,
                Subcommand.BACK,
                Subcommand.SHOW,
                Subcommand.CANCEL,
                Subcommand.AFFIRM,
                Subcommand.SELECT,
                Subcommand.COMPLETE,
                Subcommand.WISHLIST,
                Subcommand.ADD_GAME,
                Subcommand.REMOVE_GAME,
                Subcommand.REGION,
                Subcommand.NOTIFICATION
        );
    }

    @Override
    public Set<Command> getCommands() {
        return Set.of(
                Command.REGISTER,
                Command.WISHLIST,
                Command.ADD_GAME,
                Command.REMOVE_GAME,
                Command.REGION,
                Command.NOTIFICATION
        );
    }
}
