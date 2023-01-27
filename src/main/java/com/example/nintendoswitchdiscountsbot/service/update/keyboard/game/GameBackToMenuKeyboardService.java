package com.example.nintendoswitchdiscountsbot.service.update.keyboard.game;

import com.example.nintendoswitchdiscountsbot.business.CallbackData;
import com.example.nintendoswitchdiscountsbot.business.Game;
import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.utils.KeyboardHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class GameBackToMenuKeyboardService implements GameKeyboardService {

    private final static int NUMBER_OF_ROWS = 2;
    private final static int FIRST_ROW_INDEX = 0;

    private final KeyboardHelper keyboardHelper;

    @Override
    public InlineKeyboardMarkup getMarkup(CallbackData callbackData) {
        var rows = keyboardHelper.getRows(NUMBER_OF_ROWS);
        rows.get(FIRST_ROW_INDEX).add(keyboardHelper.getButton(
                "Назад в меню",
                new CallbackData(
                        Command.MENU,
                        Optional.of(Subcommand.ACCEPT),
                        Optional.empty(),
                        Optional.empty()
                )
        ));
        return new InlineKeyboardMarkup(rows);

    }

    @Override
    public Set<Subcommand> getSubcommands() {
        return Set.of(
                Subcommand.BACK,
                Subcommand.ADD_GAME
        );
    }

    @Override
    public Set<Command> getCommands() {
        return Set.of(
                Command.ADD_GAME
        );
    }

    @Override
    public InlineKeyboardMarkup getGamesMarkup(CallbackData callbackData, List<Game> games) {
        return getMarkup(callbackData);
    }
}
