package com.example.nintendoswitchdiscountsbot.service.keyboard.game;

import com.example.nintendoswitchdiscountsbot.business.CallbackData;
import com.example.nintendoswitchdiscountsbot.business.Game;
import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.service.utils.KeyboardHelper;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Component
@RequiredArgsConstructor
public class GameAffirmKeyboardService implements GameKeyboardService {

    private final static int NUMBER_OF_ROWS = 1;
    private final static int FIRST_ROW_INDEX = 0;

    private final KeyboardHelper keyboardHelper;

    @Override
    public InlineKeyboardMarkup getMarkup(CallbackData callbackData) {
        var rows = keyboardHelper.getRows(NUMBER_OF_ROWS);
        rows.get(FIRST_ROW_INDEX).addAll(List.of(
                keyboardHelper.getButton(
                callbackData
                        .toBuilder()
                        .command(Command.G_ADD)
                        .subcommand(Optional.of(Subcommand.CANCEL))
                        .build()
                ),
                keyboardHelper.getButton(
                        callbackData
                                .toBuilder()
                                .command(Command.G_ADD)
                                .subcommand(Optional.of(Subcommand.COMPLETE))
                                .build()
                )
        ));
        return new InlineKeyboardMarkup(rows);
    }

    @Override
    public Set<Subcommand> getSubcommands() {
        return Set.of(Subcommand.AFFIRM);
    }

    @Override
    public Set<Command> getCommands() {
        return Set.of(
                Command.G_ADD
        );
    }

    @Override
    public InlineKeyboardMarkup getGamesMarkup(CallbackData callbackData, List<Game> games) {
        return getMarkup(callbackData);
    }
}
