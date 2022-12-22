package com.example.nintendoswitchdiscountsbot.service.command.processor.callback;

import com.example.nintendoswitchdiscountsbot.enums.Command;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public interface CallbackCommandProcessor {

    void process(CallbackQuery callbackQuery, CallbackCommandData commandData);

    Command getCommand();
}
