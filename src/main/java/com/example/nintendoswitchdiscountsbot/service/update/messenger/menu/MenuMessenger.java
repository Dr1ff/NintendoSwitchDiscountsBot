package com.example.nintendoswitchdiscountsbot.service.update.messenger.menu;

import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.service.update.messenger.Messenger;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;


public abstract class MenuMessenger implements Messenger {

    @Override
    public Command getCommand() {
        return Command.MENU;
    }

    public abstract void reply(Long chatId, InlineKeyboardMarkup replyMarkup);

    public abstract void reply(
            CallbackQuery callbackQuery,
            InlineKeyboardMarkup replyMarkup
    );
}
