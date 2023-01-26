package com.example.nintendoswitchdiscountsbot.service.update.processor.message;

import com.example.nintendoswitchdiscountsbot.enums.Command;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface MessageNonCommandProcessor {

    void process(Message message);

    Command getState();
}
