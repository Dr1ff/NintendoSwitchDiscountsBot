package com.example.nintendoswitchdiscountsbot.service.keyboard;

import com.example.nintendoswitchdiscountsbot.business.CallbackData;
import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.Set;

public interface KeyboardService {

    InlineKeyboardMarkup getMarkup(CallbackData callbackData);

    Set<Subcommand> getSubcommands();

    Set<Command> getCommands();
}
