package com.example.nintendoswitchdiscountsbot.service.update.processor.callback.command;

import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.business.CallbackData;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public interface CallbackCommandProcessor {

    void process(CallbackQuery callbackQuery, CallbackData callbackData);

    Command getCommand();
}
