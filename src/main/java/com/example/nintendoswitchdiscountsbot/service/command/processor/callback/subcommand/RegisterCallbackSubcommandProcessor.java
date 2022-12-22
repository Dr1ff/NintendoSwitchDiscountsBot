package com.example.nintendoswitchdiscountsbot.service.command.processor.callback.subcommand;

import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.service.command.processor.callback.CallbackCommandData;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.Set;

public class RegisterCallbackSubcommandProcessor implements CallbackSubcommandProcessor {
    @Override
    public void process(CallbackQuery callbackQuery, CallbackCommandData commandData) {
    }

    @Override
    public Set<Subcommand> getSubcommand() {
        return Set.of(Subcommand.PREV, Subcommand.NEXT);
    }
}
