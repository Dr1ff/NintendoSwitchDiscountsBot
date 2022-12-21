package com.example.nintendoswitchdiscountsbot.service.command;

import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.service.observer.MessageEventPublisher;
import com.example.nintendoswitchdiscountsbot.service.command.keyboard.KeyboardService;
import com.vdurmont.emoji.EmojiParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class StartCommandProcessor implements CommandProcessor {
    private final KeyboardService keyboardService;
    private final MessageEventPublisher messageEventPublisher;
    @Override
    public void process(Update update) {
//        var user = getUser(update.getMessage().getFrom().getId());
        SendMessage message = new SendMessage();
        message.setText(getMessageText(update.getMessage().getFrom().getFirstName()));
        message.setReplyMarkup(keyboardService.getKeyboard("1"));
        message.setChatId(update.getMessage().getChatId());
        messageEventPublisher.publish(message);
    }

    @Override
    public Command getCommand() {
        return Command.START;
    }

//    private User getUser(Long userId) {
//        var userO = userStorageService.findById(userId);
//        User user;
//        if (userO.isEmpty()) {
//            user = new User(userId);
//            userStorageService.add(user);
//        } else {
//            user = userO.get();
//        }
//        return user;
//    }

    private String getMessageText(String userFirstName) {
        return EmojiParser.parseToUnicode("Привет, " +
                userFirstName +
                ", я Nintendo Switch Discount Bot " +
                ":robot_face:" +
                ":wave:" +
                "\nДля начала работы выберите ваш регион:");
    }


}
