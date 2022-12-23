package com.example.nintendoswitchdiscountsbot.service.command.reply;

import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.service.command.processor.callback.CallbackCommandData;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.Set;

public interface RegisterReplyBuilder {

    void build(
            CallbackQuery callbackQuery,
            CallbackCommandData commandData,
            InlineKeyboardMarkup replyMarkup
            );

    Set<Subcommand> getSubcommand();
}
