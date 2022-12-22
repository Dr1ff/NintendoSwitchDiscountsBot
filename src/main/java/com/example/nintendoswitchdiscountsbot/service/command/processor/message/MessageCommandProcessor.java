package com.example.nintendoswitchdiscountsbot.service.command.processor.message;

import com.example.nintendoswitchdiscountsbot.enums.Command;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface MessageCommandProcessor {

    void process(Message message);

    Command getCommand();
}
