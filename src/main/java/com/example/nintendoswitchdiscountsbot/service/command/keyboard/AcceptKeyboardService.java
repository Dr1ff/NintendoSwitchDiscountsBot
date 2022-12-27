package com.example.nintendoswitchdiscountsbot.service.command.keyboard;

import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.service.command.processor.callback.CallbackData;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vdurmont.emoji.EmojiManager;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;
import java.util.Set;
@Component
@RequiredArgsConstructor
public class AcceptKeyboardService implements KeyboardService {

    private final ObjectMapper objectMapper;

    @Override
    @SneakyThrows
    public InlineKeyboardMarkup getMarkup(CallbackData callbackData) {
        return InlineKeyboardMarkup
                .builder()
                .keyboardRow(
                        List.of(
                                InlineKeyboardButton.builder()
                                        .text("Пуск" + EmojiManager.getForAlias("rocket").getUnicode())
                                        .callbackData(
                                                objectMapper.writeValueAsString(
                                                        callbackData.toBuilder()
                                                                .command(Command.BREAK)
                                                                .build())
                                        )
                                        .build()
                        )
                )
                .build();
    }

    @Override
    public Set<Subcommand> getSubcommand() {
        return Set.of(Subcommand.ACCEPT);
    }
}
