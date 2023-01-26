package com.example.nintendoswitchdiscountsbot.service.update.processor.callback.subcommand;

import com.example.nintendoswitchdiscountsbot.business.CallbackData;
import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.Set;

public interface CallbackSubcommandProcessor {

    void process(CallbackQuery callbackQuery, CallbackData callbackData);

    Set<Subcommand> getSubcommand();

    Command getCommand();
}
