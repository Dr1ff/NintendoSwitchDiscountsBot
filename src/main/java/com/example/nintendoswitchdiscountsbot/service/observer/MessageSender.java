package com.example.nintendoswitchdiscountsbot.service.observer;

import com.example.nintendoswitchdiscountsbot.service.TelegramBot;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

@Component
@RequiredArgsConstructor
public class MessageSender {

    private final TelegramBot telegramBot;

    @SneakyThrows
    public void send(SendMessage sendMessage) {
        telegramBot.execute(sendMessage);
    }

    @SneakyThrows
    public void send(EditMessageReplyMarkup editMessageReplyMarkup) {
        telegramBot.execute(editMessageReplyMarkup);
    }

    @SneakyThrows
    public void send(EditMessageText messageText) {
        telegramBot.execute(messageText);
    }

    @SneakyThrows
    public void send(DeleteMessage deleteMessage) {
        telegramBot.execute(deleteMessage);
    }
}
