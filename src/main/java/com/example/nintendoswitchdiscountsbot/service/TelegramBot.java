package com.example.nintendoswitchdiscountsbot.service;


import com.example.nintendoswitchdiscountsbot.config.BotConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {

    private final BotConfig config;
    private final UpdateProcessor processor;

    @Override
    public String getBotUsername() {
        return config.getUsername();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        Optional<SendMessage> messageO = processor.processing(update);
        if (messageO.isPresent()) {
            try {
                execute(messageO.get());
            } catch (TelegramApiException e) {
                throw new RuntimeException("Хуйня какая то!");
            }
        }
    }
}


