package com.example.nintendoswitchdiscountsbot.service.command.processor.callback.command;

import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.service.command.processor.callback.CallbackData;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Component
public class BreakCallbackCommandProcessor implements CallbackCommandProcessor {

    @Override
    public void process(CallbackQuery callbackQuery, CallbackData callbackData) {

    }

    @Override
    public Command getCommand() {
        return Command.BREAK;
    }
}
