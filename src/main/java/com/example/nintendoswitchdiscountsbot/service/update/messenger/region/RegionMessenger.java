package com.example.nintendoswitchdiscountsbot.service.update.messenger.region;

import com.example.nintendoswitchdiscountsbot.business.CallbackData;
import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.service.update.messenger.Messenger;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public abstract class RegionMessenger implements Messenger {

    public abstract void reply(
            CallbackQuery callbackQuery,
            CallbackData callbackData,
            InlineKeyboardMarkup replyMarkup
    );

    @Override
    public Command getCommand() {
        return Command.REGION;
    }
}
