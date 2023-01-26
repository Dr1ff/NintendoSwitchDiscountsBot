package com.example.nintendoswitchdiscountsbot.service.update.keyboard;

import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.business.CallbackData;
import com.example.nintendoswitchdiscountsbot.service.update.processor.callback.data.CallbackDataMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class KeyboardHelper {

    private final CallbackDataMapper callbackDataMapper;

    public InlineKeyboardButton getEmpty() {
        return InlineKeyboardButton.builder()
                .text(" ")
                .callbackData(
                        callbackDataMapper.getJson(new CallbackData(
                                Command.BREAK,
                                Optional.empty(),
                                Optional.empty(),
                                Optional.empty()
                        ))
                )
                .build();
    }

    @SneakyThrows
    public InlineKeyboardButton getButton(CallbackData callbackData) {
        return InlineKeyboardButton.builder()
                .text(
                        callbackData
                                .subcommand()
                                .orElseThrow(() -> new IllegalArgumentException(
                                                "В KeyboardService попала callbackData " +
                                                        "с subcommandArgs = Optional.empty"
                                        )
                                )
                                .getButtonText()
                )
                .callbackData(callbackDataMapper.getJson(callbackData))
                .build();
    }

    @SneakyThrows
    public InlineKeyboardButton getButton(String text, CallbackData callbackData) {
        return InlineKeyboardButton.builder()
                .text(text)
                .callbackData(callbackDataMapper.getJson(callbackData))
                .build();
    }

    @SneakyThrows
    public InlineKeyboardButton getButton(
            String text,
            String url,
            CallbackData callbackData
    ) {
        return InlineKeyboardButton.builder()
                .text(text)
                .url(url)
                .callbackData(callbackDataMapper.getJson(callbackData))
                .build();
    }

    public List<List<InlineKeyboardButton>> getRows(int numberOfRows) {
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        for (int i = 0; i < numberOfRows; i++) {
            rows.add(new ArrayList<>());
        }
        return rows;
    }
}
