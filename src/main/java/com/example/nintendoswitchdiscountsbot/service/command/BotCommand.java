package com.example.nintendoswitchdiscountsbot.service.command;

import com.example.nintendoswitchdiscountsbot.enums.Command;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface BotCommand {

    SendMessage send(Update update);

    Command getCommand();
}
