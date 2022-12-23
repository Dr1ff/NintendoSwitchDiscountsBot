package com.example.nintendoswitchdiscountsbot.service.keyboard;

import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.service.command.processor.callback.CallbackCommandData;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ConfirmKeyboardService implements KeyboardService{

    private final ObjectMapper objectMapper;


    public InlineKeyboardMarkup getMarkup(CallbackCommandData commandData) {
        var row = new ArrayList<InlineKeyboardButton>();
        row.add(getButton(parseToCancel(commandData)));
        row.add(getButton(parseToAssert(commandData)));
        return new InlineKeyboardMarkup(List.of(row));
    }

    @SneakyThrows
    private InlineKeyboardButton getButton(CallbackCommandData commandData) {
        var button = new InlineKeyboardButton();
        button.setText(commandData.getSubcommand().getButtonText());
        button.setCallbackData(objectMapper.writeValueAsString(commandData));
        return button;
    }
    private CallbackCommandData parseToAssert(CallbackCommandData commandData) {
        var assertData = new CallbackCommandData();
        assertData.setCommand(Command.REGISTER);
        assertData.setSubcommand(Subcommand.ASSERT);
        assertData.setSubcommandArgs(List.of(
                commandData.getSubcommandArgs().get(0)
        ));
        return assertData;
    }

    private CallbackCommandData parseToCancel(CallbackCommandData commandData) {
        var cancelData = new CallbackCommandData();
        int indicator = (Integer.parseInt(commandData.getSubcommandArgs().get(1)) / 9) * 9;
        cancelData.setCommand(Command.REGISTER);
        cancelData.setSubcommand(Subcommand.CANCEL);
        cancelData.setSubcommandArgs(List.of(String.valueOf(indicator)));
        return cancelData;
    }

    @Override
    public Set<Subcommand> getSubcommand() {
        return Set.of(Subcommand.CONFIRM);
    }
}
