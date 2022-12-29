package com.example.nintendoswitchdiscountsbot.service.update.keyboard;

import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.enums.Country;
import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.business.CallbackData;
import com.example.nintendoswitchdiscountsbot.service.update.processor.callback.data.CallbackDataMapper;
import com.example.nintendoswitchdiscountsbot.service.update.processor.callback.subcommand.args.CountrySubcommandArgs;
import com.example.nintendoswitchdiscountsbot.service.update.processor.callback.subcommand.args.IntSubcommandArgs;
import com.vdurmont.emoji.EmojiManager;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.*;

@Service
public class CountryKeyboardService implements KeyboardService {

    private final static int NUMBER_OF_COUNTRY_BUTTONS = 9;
    private final static int BUTTONS_IN_ROW = 3;
    private final static int NUMBER_OF_COUNTRY_ROWS = 3;
    private final static int ARROWS_ROW_INDEX = 3;

    private final KeyboardHelper keyboardHelper;
    private final List<InlineKeyboardButton> countryButtons;

    public CountryKeyboardService(KeyboardHelper keyboardHelper, CallbackDataMapper callbackDataMapper) {
        this.keyboardHelper = keyboardHelper;
        this.countryButtons = Arrays.stream(Country.values())
                .map(this::getCountryButton)
                .toList();
    }

    @Override
    public Set<Subcommand> getSubcommand() {
        return Set.of(
                Subcommand.PREV,
                Subcommand.NEXT,
                Subcommand.CANCEL
        );
    }

    @Override
    public InlineKeyboardMarkup getMarkup(CallbackData callbackData) {
        var rows = keyboardHelper.getRows(NUMBER_OF_COUNTRY_ROWS + 1);
        int firstButtonIndex = (
                ((IntSubcommandArgs) callbackData
                        .subcommandArgs()
                        .orElseThrow(() -> new IllegalArgumentException(
                                        "В CountryKeyboardService попала callbackData " +
                                                "с subcommandArgs = Optional.empty"
                                )
                        )).i()
        );
        int nextKeyboardFirstButtonIndex = getNextKeyboardFirstIndex(firstButtonIndex);
        int prevKeyboardFirstButtonIndex = getPrevKeyboardFirstIndex(firstButtonIndex);

        if (isFirstPage(firstButtonIndex)) {
            rows.get(ARROWS_ROW_INDEX).add(keyboardHelper.getEmpty());
        } else {
            rows.get(ARROWS_ROW_INDEX).add(keyboardHelper.getButton(
                            callbackData.toBuilder()
                                    .subcommand(Optional.of(Subcommand.PREV))
                                    .subcommandArgs(Optional.of(new IntSubcommandArgs(prevKeyboardFirstButtonIndex)))
                                    .build()
                    )
            );
        }

        if (isLastPage(nextKeyboardFirstButtonIndex)) {
            nextKeyboardFirstButtonIndex = countryButtons.size();
            rows.get(ARROWS_ROW_INDEX).add(keyboardHelper.getEmpty());
        } else {
            rows.get(ARROWS_ROW_INDEX).add(keyboardHelper.getButton(
                            callbackData.toBuilder()
                                    .subcommand(Optional.of(Subcommand.NEXT))
                                    .subcommandArgs(Optional.of(new IntSubcommandArgs(nextKeyboardFirstButtonIndex)))
                                    .build()
                    )
            );
        }

        List<InlineKeyboardButton> countryButtonsSublist =
                countryButtons.subList(firstButtonIndex, nextKeyboardFirstButtonIndex);
        Iterator<InlineKeyboardButton> buttonIterator =
                countryButtonsSublist.iterator();

        assembleRows(rows, buttonIterator);

        return new InlineKeyboardMarkup(rows);
    }

    public InlineKeyboardMarkup getFirstPageKeyboardMarkup() {
        return getMarkup(CallbackData.builder()
                .command(Command.REGISTER)
                .commandArgs(Optional.empty())
                .subcommand(Optional.empty())
                .subcommandArgs(Optional.of(new IntSubcommandArgs(0)))
                .build()
        );
    }

    private int getNextKeyboardFirstIndex(int index) {
        return index + NUMBER_OF_COUNTRY_BUTTONS;
    }

    private int getPrevKeyboardFirstIndex(int index) {
        return index - NUMBER_OF_COUNTRY_BUTTONS;
    }

    private boolean isLastPage(int index) {
        return index > countryButtons.size() - 1;
    }

    private boolean isFirstPage(int index) {
        return index <= 0;
    }

    private void assembleRows(
            List<List<InlineKeyboardButton>> rows,
            Iterator<InlineKeyboardButton> buttonIterator
    ) {
        for (int i = 0; i < NUMBER_OF_COUNTRY_ROWS; i++) {
            assembleRow(rows.get(i), buttonIterator);
        }
    }

    private void assembleRow(
            List<InlineKeyboardButton> row,
            Iterator<InlineKeyboardButton> iterator
    ) {
        for (int i = 0; i < BUTTONS_IN_ROW && iterator.hasNext(); i++) {
            row.add(iterator.next());
        }
    }

    private InlineKeyboardButton getCountryButton(Country country) {
        return keyboardHelper.getButton(
                EmojiParser.parseToUnicode(country.name())
                + EmojiManager.getForAlias(country.name().toLowerCase()).getUnicode(),
                CallbackData.builder()
                        .command(Command.REGISTER)
                        .commandArgs(Optional.empty())
                        .subcommand(Optional.of(Subcommand.CONFIRM))
                        .subcommandArgs(Optional.of(new CountrySubcommandArgs(country)))
                        .build()
                );
    }
}
