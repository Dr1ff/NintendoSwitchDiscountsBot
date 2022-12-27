package com.example.nintendoswitchdiscountsbot.service.command.processor.callback.command;

import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.service.command.processor.callback.CallbackData;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public interface CallbackCommandProcessor {

    void process(CallbackQuery callbackQuery, CallbackData callbackData);

    Command getCommand();
}
