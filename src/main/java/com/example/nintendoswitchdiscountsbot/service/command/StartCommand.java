package com.example.nintendoswitchdiscountsbot.service.command;

import com.example.nintendoswitchdiscountsbot.dto.User;
import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.service.storage.UserStorageService;
import com.vdurmont.emoji.EmojiParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class StartCommand implements BotCommand {
    private final UserStorageService userStorageService;
    @Override
    public SendMessage send(Update update) {
        var user = getUser(update.getMessage().getFrom().getId());
        return new SendMessage(String.valueOf(user.getId()),
                getMessageText(update.getMessage().getChat().getFirstName()));
    }

    @Override
    public Command getCommand() {
        return Command.START;
    }

    private User getUser(Long userId) {
        var userO = userStorageService.get(userId);
        User user;
        if (userO.isEmpty()) {
            user = new User(userId);
            userStorageService.add(user);
        } else {
            user = userO.get();
        }
        return user;
    }

    private String getMessageText(String userFirstName) {
        return EmojiParser.parseToUnicode("Привет, " +
                userFirstName +
                ", я Nintendo Switch Discount Bot " +
                ":robot_face:" +
                ":wave:");
    }
}
