package com.example.nintendoswitchdiscountsbot.service.update.processor.message;

import com.example.nintendoswitchdiscountsbot.enums.Command;
import java.util.Set;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface MessageNonCommandProcessor {

    void process(Message message);

    Set<Command> getState();
}
