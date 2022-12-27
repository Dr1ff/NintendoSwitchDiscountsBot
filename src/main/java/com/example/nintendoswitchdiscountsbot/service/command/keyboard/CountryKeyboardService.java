package com.example.nintendoswitchdiscountsbot.service.command.keyboard;

import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.enums.Country;
import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.service.command.processor.callback.CallbackData;
import com.example.nintendoswitchdiscountsbot.service.command.processor.callback.CallbackParser;
import com.example.nintendoswitchdiscountsbot.service.command.processor.callback.subcommand.CountrySubcommandArgs;
import com.example.nintendoswitchdiscountsbot.service.command.processor.callback.subcommand.IntSubcommandArgs;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vdurmont.emoji.EmojiManager;
import com.vdurmont.emoji.EmojiParser;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.*;

@Service
public class CountryKeyboardService implements KeyboardService {

    private final static int NUMBER_OF_COUNTRY_BUTTONS = 9;
    private final static int BUTTONS_IN_ROW = 3;
    private final static int NUMBER_OF_ROWS = 3;
    private final static int ARROWS_ROW_INDEX = 3;

    private final CallbackParser callbackParser;
    private final ObjectMapper objectMapper;
    private final List<InlineKeyboardButton> countryButtons;

    public CountryKeyboardService(ObjectMapper objectMapper, CallbackParser callbackParser) {
        this.objectMapper = objectMapper;
        this.callbackParser = callbackParser;
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
        var rows = getRows();
        int firstButtonIndex = (((IntSubcommandArgs) callbackData.subcommandArgs()).firstButtonIndex());
        int nextKeyboardFirstButtonIndex = getNextKeyboardFirstIndex(firstButtonIndex);
        int prevKeyboardFirstButtonIndex = getPrevKeyboardFirstIndex(firstButtonIndex);

        if (isFirstPage(firstButtonIndex)) {
            rows.get(ARROWS_ROW_INDEX).add(getEmptyButton());
        } else {
            rows.get(ARROWS_ROW_INDEX).add(getButton(
                            callbackData.toBuilder()
                                    .subcommand(Subcommand.PREV)
                                    .subcommandArgs(
                                            IntSubcommandArgs.builder()
                                                    .firstButtonIndex(prevKeyboardFirstButtonIndex)
                                                    .build()
                                    )
                                    .build()
                    )
            );
        }

        if (isLastPage(nextKeyboardFirstButtonIndex)) {
            nextKeyboardFirstButtonIndex = countryButtons.size();
            rows.get(ARROWS_ROW_INDEX).add(getEmptyButton());
        } else {
            rows.get(ARROWS_ROW_INDEX).add(getButton(
                            callbackData.toBuilder()
                                    .subcommand(Subcommand.NEXT)
                                    .subcommandArgs(
                                            IntSubcommandArgs.builder()
                                                    .firstButtonIndex(nextKeyboardFirstButtonIndex)
                                                    .build()
                                    )
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
                .subcommandArgs(IntSubcommandArgs.builder()
                        .firstButtonIndex(0)
                        .build()
                )
                .build()
        );
    }

    private List<List<InlineKeyboardButton>> getRows() {
        return List.of(
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>()
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

    @SneakyThrows
    private InlineKeyboardButton getButton(CallbackData callbackData) {
        return InlineKeyboardButton.builder()
                .text(callbackData.subcommand().getButtonText())
                .callbackData(
                        objectMapper.writeValueAsString(
                                callbackParser.fromData(callbackData)
                        )
                )
                .build();
    }

    @SneakyThrows
    private InlineKeyboardButton getEmptyButton() {
        return InlineKeyboardButton.builder()
                .text(" ")
                .callbackData(
                        objectMapper.writeValueAsString(callbackParser.fromData(
                                        CallbackData.builder()
                                                .command(Command.BREAK)
                                                .build()
                                )
                        )
                )
                .build();
    }

    private void assembleRows(List<List<InlineKeyboardButton>> rows, Iterator<InlineKeyboardButton> buttonIterator) {
        for (int i = 0; i < NUMBER_OF_ROWS; i++) {
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

    @SneakyThrows
    private InlineKeyboardButton getCountryButton(Country country) {
        return InlineKeyboardButton.builder()
                .text( /* Страна и ее флаг(emoji) */
                        EmojiParser.parseToUnicode(country.name())
                                + EmojiManager.getForAlias(country.name().toLowerCase(Locale.ROOT)).getUnicode())
                .callbackData(objectMapper.writeValueAsString(callbackParser.fromData(
                                        CallbackData.builder()
                                                .command(Command.REGISTER)
                                                .subcommand(Subcommand.CONFIRM)
                                                .subcommandArgs(
                                                        CountrySubcommandArgs.builder()
                                                                .country(country)
                                                                .build()
                                                )
                                                .build()
                                )
                        )
                )
                .build();
    }
}
