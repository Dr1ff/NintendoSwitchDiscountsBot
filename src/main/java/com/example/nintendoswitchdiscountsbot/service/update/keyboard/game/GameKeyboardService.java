package com.example.nintendoswitchdiscountsbot.service.update.keyboard.game;

import com.example.nintendoswitchdiscountsbot.business.CallbackData;
import com.example.nintendoswitchdiscountsbot.business.Game;
import com.example.nintendoswitchdiscountsbot.service.update.keyboard.KeyboardService;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;

public interface GameKeyboardService extends KeyboardService {

    InlineKeyboardMarkup getGamesMarkup(CallbackData callbackData, List<Game> games);

}
