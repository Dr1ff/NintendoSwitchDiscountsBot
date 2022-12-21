package com.example.nintendoswitchdiscountsbot.service.command;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public interface CallbackProcessor {

    void process(CallbackQuery callbackQuery);
}
