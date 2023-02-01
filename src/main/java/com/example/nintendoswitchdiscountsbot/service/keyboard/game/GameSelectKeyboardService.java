package com.example.nintendoswitchdiscountsbot.service.keyboard.game;

import com.example.nintendoswitchdiscountsbot.business.CallbackData;
import com.example.nintendoswitchdiscountsbot.business.Game;
import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.service.utils.KeyboardHelper;
import com.example.nintendoswitchdiscountsbot.service.utils.KeyboardRowAdjuster;
import com.example.nintendoswitchdiscountsbot.service.update.processor.callback.subcommand.args.integer.IntegerSubcommandArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class GameSelectKeyboardService implements GameKeyboardService {

    private final static int NUMBER_OF_GAME_BUTTONS = 5;
    private final static int BUTTONS_IN_ROW = 1;
    private final static int FIRST_BUTTON_INDEX = 0;

    private final KeyboardHelper keyboardHelper;
    private final KeyboardRowAdjuster keyboardRowAdjuster;

    @Override
    public InlineKeyboardMarkup getGamesMarkup(CallbackData callbackData, List<Game> games) {
        var rows = keyboardHelper.getRows(NUMBER_OF_GAME_BUTTONS + 1);
        var gameButtons = games
                .stream()
                .map(game -> keyboardHelper.getButton(
                                game.name(),
                                callbackData.toBuilder()
                                        .subcommand(Optional.of(Subcommand.AFFIRM))
                                        .subcommandArgs(Optional.of(new IntegerSubcommandArgs(game.hashcode())))
                                        .build()
                        )
                )
                .toList();
        int lastButtonIndex = NUMBER_OF_GAME_BUTTONS;
        if (lastButtonIndex > games.size()) {
            lastButtonIndex = games.size();
        }
        keyboardRowAdjuster.assembleRows(
                rows,
                gameButtons.subList(FIRST_BUTTON_INDEX, lastButtonIndex).iterator(),
                NUMBER_OF_GAME_BUTTONS,
                BUTTONS_IN_ROW
        );

        return new InlineKeyboardMarkup(rows);
    }

    @Override
    public InlineKeyboardMarkup getMarkup(CallbackData callbackData) {
        return new InlineKeyboardMarkup();
    }

    @Override
    public Set<Subcommand> getSubcommands() {
        return Set.of(
                Subcommand.SELECT,
                Subcommand.CANCEL
        );
    }

    @Override
    public Set<Command> getCommands() {
        return Set.of(Command.ADD_GAME);
    }

}





