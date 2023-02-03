package com.example.nintendoswitchdiscountsbot.service.keyboard.clean;

import com.example.nintendoswitchdiscountsbot.business.CallbackData;
import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.service.keyboard.KeyboardService;
import com.example.nintendoswitchdiscountsbot.service.utils.KeyboardHelper;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Component
@RequiredArgsConstructor
public class CleanKeyboardService implements KeyboardService {

    private final static int ROWS = 1;
    private final static int FIRST_ROW_INDEX = 0;

    private final KeyboardHelper keyboardHelper;

    @Override
    public InlineKeyboardMarkup getMarkup(CallbackData callbackData) {
        var rows = keyboardHelper.getRows(ROWS);
        rows.get(FIRST_ROW_INDEX).add(
                keyboardHelper.getButton(callbackData)
        );
        return new InlineKeyboardMarkup(rows);
    }

    @Override
    public Set<Subcommand> getSubcommands() {
        return Set.of(Subcommand.CLEAN);
    }

    @Override
    public Set<Command> getCommands() {
        return Set.of(Command.BREAK);
    }
}
