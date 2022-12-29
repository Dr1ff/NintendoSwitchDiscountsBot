package com.example.nintendoswitchdiscountsbot.service.command.processor.callback;

import com.example.nintendoswitchdiscountsbot.enums.Command;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Component
public class BreakCallbackCommandProcessor implements CallbackCommandProcessor{
    @Override
    public void process(CallbackQuery callbackQuery, CallbackCommandData commandData) {

    }

    @Override
    public Command getCommand() {
        return Command.BREAK;
    }
}
