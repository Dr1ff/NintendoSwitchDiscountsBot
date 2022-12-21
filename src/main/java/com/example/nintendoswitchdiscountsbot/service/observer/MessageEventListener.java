package com.example.nintendoswitchdiscountsbot.service.observer;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

@Component
@RequiredArgsConstructor
public class MessageEventListener {

    private final MessageSender messageSender;

    @EventListener
    public void handle(SendMessage sendMessage) {
        messageSender.send(sendMessage);
    }

    @EventListener
    public void handle(EditMessageReplyMarkup editMessageReplyMarkup) {
        messageSender.send(editMessageReplyMarkup);
    }

    @EventListener
    public void handle(EditMessageText messageText) {
        messageSender.send(messageText);
    }
}
