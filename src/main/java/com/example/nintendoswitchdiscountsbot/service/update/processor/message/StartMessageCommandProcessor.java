package com.example.nintendoswitchdiscountsbot.service.update.processor.message;

import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.service.update.keyboard.CountryKeyboardService;
import com.example.nintendoswitchdiscountsbot.service.observer.MessageEventPublisher;
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

    @Override
    public void process(Message message) {
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
