package com.example.nintendoswitchdiscountsbot.service.update.keyboard.add_game;

import com.example.nintendoswitchdiscountsbot.business.CallbackData;
import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.service.update.keyboard.KeyboardHelper;
import com.example.nintendoswitchdiscountsbot.service.update.keyboard.KeyboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class OneResultKeyboardService implements KeyboardService {

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
                        .command(Command.ADD_GAME)
                        .subcommand(Optional.of(Subcommand.CANCEL))
                        .build()
                ),
                keyboardHelper.getButton(
                        callbackData
                                .toBuilder()
                                .command(Command.ADD_GAME)
                                .subcommand(Optional.of(Subcommand.ACCEPT))
                                .build()
                )
        ));
        return new InlineKeyboardMarkup(rows);
    }

    @Override
    public Set<Subcommand> getSubcommands() {
        return Set.of(Subcommand.ONE_RESULT);
    }

    @Override
    public Set<Command> getCommands() {
        return Set.of(
                Command.ADD_GAME
        );
    }
}
