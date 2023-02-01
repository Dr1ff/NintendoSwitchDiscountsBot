package com.example.nintendoswitchdiscountsbot.service.keyboard.country;

import com.example.nintendoswitchdiscountsbot.business.CallbackData;
import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.enums.Country;
import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.service.utils.KeyboardHelper;
import com.example.nintendoswitchdiscountsbot.service.utils.KeyboardRowAdjuster;
import com.example.nintendoswitchdiscountsbot.service.keyboard.KeyboardService;
import com.example.nintendoswitchdiscountsbot.service.utils.PaginationKeyboardUtil;
import com.example.nintendoswitchdiscountsbot.service.update.processor.callback.subcommand.args.country.CountrySubcommandArgs;
import com.example.nintendoswitchdiscountsbot.service.update.processor.callback.subcommand.args.integer.IntegerSubcommandArgs;
import com.vdurmont.emoji.EmojiManager;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class CountryKeyboardService implements KeyboardService {

    private final static int NUMBER_OF_COUNTRY_BUTTONS = 9;
    private final static int BUTTONS_IN_ROW = 3;
    private final static int NUMBER_OF_COUNTRY_ROWS = 3;
    private final static int PAGINATION_ROW_INDEX = 3;

    private final KeyboardHelper keyboardHelper;
    private final List<InlineKeyboardButton> countryButtons;
    private final KeyboardRowAdjuster keyboardRowAdjuster;

    public CountryKeyboardService(
            KeyboardHelper keyboardHelper,
            KeyboardRowAdjuster keyboardRowAdjuster
    ) {
        this.keyboardHelper = keyboardHelper;
        this.keyboardRowAdjuster = keyboardRowAdjuster;
        this.countryButtons = Arrays.stream(Country.values())
                .map(this::getCountryButton)
                .toList();
    }

    @Override
    public Set<Subcommand> getSubcommands() {
        return Set.of(
                Subcommand.PREV,
                Subcommand.NEXT,
                Subcommand.CANCEL
        );
    }

    @Override
    public Set<Command> getCommands() {
        return Set.of(
                Command.REGISTER
        );
    }

    @Override
    public InlineKeyboardMarkup getMarkup(CallbackData callbackData) {
        var rows = keyboardHelper.getRows(NUMBER_OF_COUNTRY_ROWS + 1);
        int firstButtonIndex = PaginationKeyboardUtil.firstButtonIndex(callbackData);
        int nextKeyboardFirstButtonIndex =
                PaginationKeyboardUtil.nextKeyboardFirstIndex(firstButtonIndex, NUMBER_OF_COUNTRY_BUTTONS);
        keyboardRowAdjuster.setPaginationButtons(
                rows,
                NUMBER_OF_COUNTRY_BUTTONS,
                countryButtons.size(),
                firstButtonIndex,
                PAGINATION_ROW_INDEX,
                callbackData
        );
        if (
                PaginationKeyboardUtil.isLastPage(
                        nextKeyboardFirstButtonIndex,
                        NUMBER_OF_COUNTRY_BUTTONS
                )
        ) {
            nextKeyboardFirstButtonIndex = countryButtons.size();
        }
        keyboardRowAdjuster.assembleRows(
                rows,
                countryButtons.subList(
                                firstButtonIndex, nextKeyboardFirstButtonIndex
                        )
                        .iterator(),
                NUMBER_OF_COUNTRY_ROWS,
                BUTTONS_IN_ROW
        );
        return new InlineKeyboardMarkup(rows);
    }

    public InlineKeyboardMarkup getFirstPageKeyboardMarkup() {
        return getMarkup(CallbackData.builder()
                .command(Command.REGISTER)
                .commandArgs(Optional.empty())
                .subcommand(Optional.empty())
                .subcommandArgs(Optional.of(new IntegerSubcommandArgs(0)))
                .build()
        );
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
