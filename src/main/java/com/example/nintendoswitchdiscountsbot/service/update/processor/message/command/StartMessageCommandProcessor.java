package com.example.nintendoswitchdiscountsbot.service.update.processor.message.command;

import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.service.keyboard.country.CountryKeyboardService;
import com.example.nintendoswitchdiscountsbot.service.observer.MessageEventPublisher;
import com.example.nintendoswitchdiscountsbot.service.update.processor.message.MessageCommandProcessor;
import com.example.nintendoswitchdiscountsbot.service.utils.UserStateValidator;
import com.vdurmont.emoji.EmojiParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
@RequiredArgsConstructor
public class StartMessageCommandProcessor implements MessageCommandProcessor {

    private final CountryKeyboardService keyboardService;
    private final MessageEventPublisher messageEventPublisher;
    private final UserStateValidator userStateValidator;

    @Override
    public void process(Message message) {
        userStateValidator.validation(message.getChatId(), this);
        messageEventPublisher.publish(SendMessage.builder()
                .text(getMessageText(message.getFrom().getFirstName()))
                .replyMarkup(keyboardService.getFirstPageKeyboardMarkup())
                .chatId(message.getChatId())
                .build()
        );
    }

    @Override
    public Command getCommand() {
        return Command.START;
    }

    private String getMessageText(String userFirstName) {
        return EmojiParser.parseToUnicode("Привет, " +
                userFirstName +
                ", я Nintendo Switch Discount Bot " +
                ":robot_face:" +
                ":wave:" +
                "\nДля начала работы выберите ваш регион:");
    }
}