package com.example.nintendoswitchdiscountsbot.service.command.keyboard;

import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.service.command.processor.callback.CallbackData;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.Set;

public interface KeyboardService {

    InlineKeyboardMarkup getMarkup(CallbackData callbackData);

    Set<Subcommand> getSubcommand();
}
