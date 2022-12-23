package com.example.nintendoswitchdiscountsbot.service.command.keyboard;

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
public class CountryKeyboardService implements KeyboardService{

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
                    data.setSubcommandArgs(List.of(country.name(), String.valueOf(country.ordinal())));
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
        setSubcommandData(Subcommand.PREV, commandData, 8);
        return getMarkup(commandData);
    }

    public InlineKeyboardMarkup getMarkup(CallbackCommandData commandData) {
        if(commandData.getSubcommand().equals(Subcommand.NEXT)) {
            return getNext(commandData);
        } else if (commandData.getSubcommand().equals(Subcommand.PREV)) {
            return getPrev(commandData);
        } else {
            setSubcommandData(
                    Subcommand.NEXT,
                    commandData,
                    Integer.parseInt(commandData.getSubcommandArgs().get(0)));
            return getNext(commandData);
        }
    }

    private InlineKeyboardMarkup getNext(CallbackCommandData commandData) {
        var rows = getRows();
        int firstIndex = Integer.parseInt(commandData.getSubcommandArgs().get(0));
        int lastIndex = firstIndex + 9;
        if(firstIndex <= 0) {
            rows.get(3).add(getEmptyButton());
        } else {
            setSubcommandData(Subcommand.PREV, commandData, firstIndex - 1);
            rows.get(3).add(getButton(commandData));
        }
        if (lastIndex > countryButtons.size() - 1) {
            lastIndex = countryButtons.size();
            rows.get(3).add(getEmptyButton());
        } else {
            setSubcommandData(Subcommand.NEXT, commandData, lastIndex);
            rows.get(3).add(getButton(commandData));
        }
        List<InlineKeyboardButton> countryButtonsSublist = countryButtons.subList(firstIndex, lastIndex);
        Iterator<InlineKeyboardButton> buttonIterator = countryButtonsSublist.iterator();
        assembleRows(rows, buttonIterator);
        return new InlineKeyboardMarkup(rows);
    }

    private InlineKeyboardMarkup getPrev(CallbackCommandData commandData) {
        var rows = getRows();
        int lastIndex = Integer.parseInt(commandData.getSubcommandArgs().get(0));
        int firstIndex = lastIndex - 8;
        if(firstIndex == 0) {
            rows.get(3).add(getEmptyButton());
        } else {
            setSubcommandData(Subcommand.PREV, commandData, firstIndex - 1);
            rows.get(3).add(getButton(commandData));
        }
        setSubcommandData(Subcommand.NEXT, commandData, ++lastIndex);
        rows.get(3).add(getButton(commandData));


        List<InlineKeyboardButton> countryButtonsSublist = countryButtons.subList(firstIndex, lastIndex);
        Iterator<InlineKeyboardButton> buttonIterator = countryButtonsSublist.iterator();
        assembleRows(rows, buttonIterator);
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

    @SneakyThrows
    private InlineKeyboardButton getEmptyButton() {
        var commandData = new CallbackCommandData();
        commandData.setCommand(Command.BREAK);
        var empty = new InlineKeyboardButton(" ");
        empty.setCallbackData(objectMapper.writeValueAsString(commandData));
        return empty;
    }

    private void assembleRows(List<List<InlineKeyboardButton>> rows, Iterator<InlineKeyboardButton> buttonIterator) {
        assembleRow(rows.get(0), buttonIterator);
        assembleRow(rows.get(1), buttonIterator);
        assembleRow(rows.get(2), buttonIterator);
    }
    private void assembleRow(
            List<InlineKeyboardButton> row,
            Iterator<InlineKeyboardButton> iterator
        ) {
                for(int i = 0; i < 3 && iterator.hasNext(); i++) {
                row.add(iterator.next());
                }
    }

    private List<List<InlineKeyboardButton>> getRows() {
        return List.of(
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>()
        );
    }

    public Set<Subcommand> getSubcommand() {
        return Set.of(Subcommand.PREV, Subcommand.NEXT, Subcommand.CANCEL);
    }
}
