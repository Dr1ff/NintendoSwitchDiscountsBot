package com.example.nintendoswitchdiscountsbot.service.command.register;

import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.enums.Country;
import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.service.command.processor.callback.CallbackCommandData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vdurmont.emoji.EmojiManager;
import com.vdurmont.emoji.EmojiParser;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.*;

@Service
public class CountryKeyboardService {

    private final List<InlineKeyboardButton> countryButtons;
    private final ObjectMapper objectMapper;


    public CountryKeyboardService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.countryButtons = Arrays
                .stream(Country.values())
                .map(country -> {
                    InlineKeyboardButton button = new InlineKeyboardButton();
                    button.setText(
                            EmojiParser.parseToUnicode(country.name())
                                    + EmojiManager.getForAlias(country.name().toLowerCase(Locale.ROOT)).getUnicode()
                    );
                    CallbackCommandData data = new CallbackCommandData();
                    data.setCommand(Command.REGISTER);
                    data.setSubcommand(Subcommand.CONFIRM);
                    data.setSubcommandArgs(List.of(country.name()));
                    try {
                        button.setCallbackData(objectMapper.writeValueAsString(data));
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                    return button;
                }).toList();
    }

    public InlineKeyboardMarkup getMarkup() {
        var commandData = new CallbackCommandData();
        commandData.setCommand(Command.REGISTER);
        commandData.setSubcommand(Subcommand.PREV);
        commandData.setSubcommandArgs(List.of(
                String.valueOf(11),
                commandData.getSubcommand().getButtonText()
        ));
        return getMarkup(commandData);
    }

    public InlineKeyboardMarkup getMarkup(CallbackCommandData commandData) {
        if(commandData.getSubcommand().equals(Subcommand.NEXT)) {
            return getNext(commandData);
        } else {
            return getPrev(commandData);
        }
    }

    private InlineKeyboardMarkup getNext(CallbackCommandData commandData) {
        var rows = getRows();
        List<InlineKeyboardButton> countryButtonsSublist;
        int firstIndex = Integer.parseInt(commandData.getSubcommandArgs().get(0));
        int lastIndex = firstIndex + 10;

        if (lastIndex <= countryButtons.size() - 1) {
            countryButtonsSublist = countryButtons.subList(firstIndex, lastIndex);
        } else {
            countryButtonsSublist = countryButtons.subList(firstIndex, countryButtons.size() - 1);
        }

        Iterator<InlineKeyboardButton> buttonIterator = countryButtonsSublist.iterator();
        setSubcommandData(Subcommand.PREV, commandData, firstIndex - 1);
        rows.get(1).add(getButton(commandData));
        assembleRows(rows, buttonIterator);

        if(buttonIterator.hasNext()) {
            setSubcommandData(Subcommand.NEXT, commandData, lastIndex + 1);
            rows.get(3).add(getButton(commandData));
        }
        return new InlineKeyboardMarkup(rows);
    }

    private InlineKeyboardMarkup getPrev(CallbackCommandData commandData) {
        var rows = getRows();
        List<InlineKeyboardButton> countryButtonsSublist;
        int lastIndex = Integer.parseInt(commandData.getSubcommandArgs().get(0));
        int firstIndex = lastIndex - 10;

        if(firstIndex > 0) {
            countryButtonsSublist = countryButtons.subList(firstIndex, lastIndex);
        } else {
            countryButtonsSublist = countryButtons.subList(0, lastIndex);
        }
        Iterator<InlineKeyboardButton> buttonIterator = countryButtonsSublist.iterator();

        if(firstIndex <= 0) {
            rows.get(1).add(buttonIterator.next());
        } else {
            commandData.setSubcommandArgs(List.of(
                    String.valueOf(firstIndex - 1)));
            rows.get(1).add(getButton(commandData));
        }
        assembleRows(rows, buttonIterator);
        setSubcommandData(Subcommand.NEXT, commandData, lastIndex + 1);
        rows.get(3).add(getButton(commandData));
        return new InlineKeyboardMarkup(rows);
    }

    private void setSubcommandData(
            Subcommand subcommand,
            CallbackCommandData data,
            int pointAt
    ) {
        data.setSubcommand(subcommand);
        data.setSubcommandArgs(List.of(
                String.valueOf(pointAt),
                subcommand.getButtonText()
        ));
    }

    @SneakyThrows
    private InlineKeyboardButton getButton(CallbackCommandData commandData) {
        var button = new InlineKeyboardButton();
        button.setText(commandData.getSubcommand().getButtonText());
        button.setCallbackData(objectMapper.writeValueAsString(commandData));
        return button;
    }

    private void assembleRows(List<List<InlineKeyboardButton>> rows, Iterator<InlineKeyboardButton> buttonIterator) {
        assembleRow(rows.get(1), buttonIterator, 3);
        assembleRow(rows.get(2), buttonIterator, 4);
        assembleRow(rows.get(3), buttonIterator, 3);
    }
    private void assembleRow(
            List<InlineKeyboardButton> row,
            Iterator<InlineKeyboardButton> iterator,
            int buttons
        ) {
                for(int i = 0; i < buttons && iterator.hasNext(); i++) {
                row.add(iterator.next());
                }
    }

    private List<List<InlineKeyboardButton>> getRows() {
        return List.of(
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>()
        );
    }


}
