package com.example.nintendoswitchdiscountsbot.service.command;

import com.example.nintendoswitchdiscountsbot.enums.Command;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface CommandProcessor {

    void process(Update update);

    Command getCommand();
}
