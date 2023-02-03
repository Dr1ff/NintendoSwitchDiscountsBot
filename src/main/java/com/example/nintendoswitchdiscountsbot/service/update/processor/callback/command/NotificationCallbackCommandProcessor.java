package com.example.nintendoswitchdiscountsbot.service.update.processor.callback.command;

import com.example.nintendoswitchdiscountsbot.business.CallbackData;
import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.service.observer.MessageEventPublisher;
import com.vdurmont.emoji.EmojiParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Component
@RequiredArgsConstructor
public class NotificationCallbackCommandProcessor implements CallbackCommandProcessor {

    private final MessageEventPublisher messageEventPublisher;

    @Override
    public void process(CallbackQuery callbackQuery, CallbackData callbackData) {
        messageEventPublisher.publish(AnswerCallbackQuery.builder()
                .text(EmojiParser.parseToUnicode(
                                "Данный раздел находится в разработке.:man_technologist:"
                        )
                )
                .callbackQueryId(callbackQuery.getId())
                .showAlert(true)
                .build());
    }

    @Override
    public Command getCommand() {
        return Command.NOTIFICATION;
    }
}
