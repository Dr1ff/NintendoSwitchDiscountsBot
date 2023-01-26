package com.example.nintendoswitchdiscountsbot.service.update.keyboard.add_game;

import com.example.nintendoswitchdiscountsbot.business.CallbackData;
import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.service.update.keyboard.KeyboardHelper;
import com.example.nintendoswitchdiscountsbot.service.update.keyboard.KeyboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ZeroResultKeyboardService implements KeyboardService {

    private final static int NUMBER_OF_ROWS = 2;
    private final static int FIRST_ROW_INDEX = 0;
    private final static int SECOND_ROW_INDEX = 1;

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
        rows.get(SECOND_ROW_INDEX).add(keyboardHelper.getButton(
                "Nintendo eShop",
                "https://www.nintendo.ru/Nintendo-eShop/Nintendo-eShop-1806894.html",
                callbackData
                ));
        return new InlineKeyboardMarkup(rows);

    }

    @Override
    public Set<Subcommand> getSubcommands() {
        return Set.of(
                Subcommand.ZERO_RESULT,
                Subcommand.ADD_GAME
        );
    }

    @Override
    public Set<Command> getCommands() {
        return Set.of(
                Command.ADD_GAME
        );
    }
}
