package com.example.nintendoswitchdiscountsbot.service.observer;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

@Component
@RequiredArgsConstructor
public class MessageEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public void publish(SendMessage sendMessage) {
        applicationEventPublisher.publishEvent(sendMessage);
    }

    public void publish(EditMessageReplyMarkup editMessageReplyMarkup) {
        applicationEventPublisher.publishEvent(editMessageReplyMarkup);
    }

    public void publish(EditMessageText messageText) {
        applicationEventPublisher.publishEvent(messageText);
    }

    public void publish(DeleteMessage deleteMessage) {
        applicationEventPublisher.publishEvent(deleteMessage);
    }
}
