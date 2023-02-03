package com.example.nintendoswitchdiscountsbot.service.keyboard.country;

import com.example.nintendoswitchdiscountsbot.business.CallbackData;
import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.enums.Country;
import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.service.keyboard.KeyboardService;
import com.example.nintendoswitchdiscountsbot.service.update.processor.callback.subcommand.args.country.CountrySubcommandArgs;
import com.example.nintendoswitchdiscountsbot.service.update.processor.callback.subcommand.args.integer.IntegerSubcommandArgs;
import com.example.nintendoswitchdiscountsbot.service.utils.KeyboardHelper;
import com.example.nintendoswitchdiscountsbot.service.utils.KeyboardRowAdjuster;
import com.example.nintendoswitchdiscountsbot.service.utils.PaginationKeyboardUtil;
import com.vdurmont.emoji.EmojiManager;
import com.vdurmont.emoji.EmojiParser;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

@Component
@RequiredArgsConstructor
public class CountryKeyboardService implements KeyboardService {

    private final static int COUNTRY_BUTTONS = 9;
    private final static int BUTTONS_IN_ROW = 3;
    private final static int COUNTRY_ROWS = 3;
    private final static int NAV_ROWS = 1;
    private final static int NAV_ROW_INDEX = 3;
    private final static int BACK_TO_MENU_ROW_INDEX = 4;
    private final static int FIRST_PAGE_INDEX = 0;

    private final KeyboardHelper keyboardHelper;
    private final KeyboardRowAdjuster keyboardRowAdjuster;

    @Override
    public Set<Subcommand> getSubcommands() {
        return Set.of(
                Subcommand.PREV,
                Subcommand.NEXT,
                Subcommand.CANCEL,
                Subcommand.REGION
        );
    }

    @Override
    public Set<Command> getCommands() {
        return Set.of(
                Command.REGISTER,
                Command.REGION
        );
    }

    @Override
    public InlineKeyboardMarkup getMarkup(CallbackData callbackData) {
        var rows = keyboardHelper.getRows(NAV_ROWS + COUNTRY_ROWS);
        int firstButtonIndex = PaginationKeyboardUtil.firstButtonIndex(callbackData);
        var countryButtons = getCountryButtonsFromCommand(callbackData.command());
        int nextKeyboardFirstButtonIndex =
                PaginationKeyboardUtil.nextKeyboardFirstIndex(firstButtonIndex, COUNTRY_BUTTONS);
        keyboardRowAdjuster.setPaginationButtons(
                rows,
                COUNTRY_BUTTONS,
                countryButtons.size(),
                firstButtonIndex,
                NAV_ROW_INDEX,
                callbackData
        );
        if (
                PaginationKeyboardUtil.isLastPage(
                        nextKeyboardFirstButtonIndex,
                        COUNTRY_BUTTONS
                )
        ) {
            nextKeyboardFirstButtonIndex = countryButtons.size();
        }
        if (isRegionCommand(callbackData)) {
            rows.add(new ArrayList<>());
            rows.get(BACK_TO_MENU_ROW_INDEX).add(keyboardHelper.getButton(
                    Subcommand.BACK.getButtonText(),
                    CallbackData.builder()
                            .command(Command.MENU)
                            .subcommand(Optional.of(Subcommand.SHOW))
                            .subcommandArgs(Optional.empty())
                            .commandArgs(Optional.empty())
                            .build()
            ));
        }
        keyboardRowAdjuster.assembleRows(
                rows,
                countryButtons.subList(
                                firstButtonIndex, nextKeyboardFirstButtonIndex
                        )
                        .iterator(),
                COUNTRY_ROWS,
                BUTTONS_IN_ROW
        );
        return new InlineKeyboardMarkup(rows);
    }

    public InlineKeyboardMarkup getFirstPageKeyboardMarkup() {
        return getMarkup(CallbackData.builder()
                .command(Command.REGISTER)
                .commandArgs(Optional.empty())
                .subcommand(Optional.empty())
                .subcommandArgs(Optional.of(new IntegerSubcommandArgs(FIRST_PAGE_INDEX)))
                .build()
        );
    }

    private boolean isRegionCommand(CallbackData callbackData) {
        return callbackData.command().equals(Command.REGION);
    }

    private List<InlineKeyboardButton> getCountryButtonsFromCommand(Command command) {
        return Arrays.stream(Country.values())
                .map(country -> getCountryButtonFromCommand(country, command))
                .toList();
    }

    private InlineKeyboardButton getCountryButtonFromCommand(Country country, Command command) {
        return keyboardHelper.getButton(
                EmojiParser.parseToUnicode(country.name())
                        + EmojiManager.getForAlias(country.name().toLowerCase()).getUnicode(),
                CallbackData.builder()
                        .command(command)
                        .commandArgs(Optional.empty())
                        .subcommand(Optional.of(Subcommand.CONFIRM))
                        .subcommandArgs(Optional.of(new CountrySubcommandArgs(country)))
                        .build()
        );
    }
}
