package com.example.nintendoswitchdiscountsbot.service.command.processor.callback.subcommand;

import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.service.command.processor.callback.CallbackData;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.Set;

public interface CallbackSubcommandProcessor {

    void process(CallbackQuery callbackQuery, CallbackData callbackData);

    Set<Subcommand> getSubcommand();
}
