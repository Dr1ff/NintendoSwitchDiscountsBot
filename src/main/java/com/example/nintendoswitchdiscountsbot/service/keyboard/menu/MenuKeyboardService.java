package com.example.nintendoswitchdiscountsbot.service.keyboard.menu;

import com.example.nintendoswitchdiscountsbot.business.CallbackData;
import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.service.keyboard.KeyboardService;
import com.example.nintendoswitchdiscountsbot.service.update.processor.callback.subcommand.args.integer.IntegerSubcommandArgs;
import com.example.nintendoswitchdiscountsbot.service.utils.KeyboardHelper;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.*;

@Component
public class MenuKeyboardService implements KeyboardService {

    private final KeyboardHelper keyboardHelper;
    private final List<Subcommand> subcommands;

    public MenuKeyboardService(KeyboardHelper keyboardHelper) {
        this.keyboardHelper = keyboardHelper;
        this.subcommands = List.of(
                Subcommand.WISHLIST,
                Subcommand.REGION,
                Subcommand.NOTIFICATION
        );
    }

    @Override
    public InlineKeyboardMarkup getMarkup(CallbackData callbackData) {
        return new InlineKeyboardMarkup(
                assembleRows(
                        keyboardHelper.getRows(subcommands.size()),
                        subcommands.iterator()
                )
        );
    }

    @Override
    public Set<Subcommand> getSubcommands() {
        return new HashSet<>(subcommands);
    }

    @Override
    public Set<Command> getCommands() {
        return Set.of(
                Command.MENU
        );
    }

    private List<List<InlineKeyboardButton>> assembleRows(
            List<List<InlineKeyboardButton>> rows,
            Iterator<Subcommand> subcommandIterator
    ) {
        for (int i = 0; subcommandIterator.hasNext(); i++) {
            var subcommand = subcommandIterator.next();
            rows.get(i).add(keyboardHelper.getButton(
                    new CallbackData(
                            Command.valueOf(subcommand.name()),
                            Optional.of(subcommand),
                            Optional.empty(),
                            Optional.of(new IntegerSubcommandArgs(0))
                    )
            ));
        }
        return rows;
    }
}
