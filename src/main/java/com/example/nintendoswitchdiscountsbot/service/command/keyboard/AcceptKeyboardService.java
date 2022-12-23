package com.example.nintendoswitchdiscountsbot.service.command.keyboard;

import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.service.command.processor.callback.CallbackCommandData;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vdurmont.emoji.EmojiManager;
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
public class AcceptKeyboardService implements KeyboardService {
    private final ObjectMapper objectMapper;
    @Override
    @SneakyThrows
    public InlineKeyboardMarkup getMarkup(CallbackCommandData commandData) {
        var acceptButton = new InlineKeyboardButton();
        var row = new ArrayList<InlineKeyboardButton>();
        acceptButton.setText("Пуск" + EmojiManager.getForAlias("rocket").getUnicode());
        commandData.setCommand(Command.BREAK);
        acceptButton.setCallbackData(objectMapper.writeValueAsString(commandData));
        row.add(acceptButton);
        return new InlineKeyboardMarkup(List.of(row));
    }

    @Override
    public Set<Subcommand> getSubcommand() {
        return Set.of(Subcommand.ACCEPT);
    }
}
