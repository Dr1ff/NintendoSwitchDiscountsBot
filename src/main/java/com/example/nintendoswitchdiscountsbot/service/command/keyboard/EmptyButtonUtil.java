package com.example.nintendoswitchdiscountsbot.service.command.keyboard;

import lombok.experimental.UtilityClass;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

@UtilityClass
public class EmptyButtonUtil {

    public static InlineKeyboardButton get() {
        return InlineKeyboardButton.builder()
                .text(" ")
                .callbackData("{\"c\":\"BREAK\",\"s\":null,\"ca\":null,\"sa\":null}") //хз можно ли по другому?
                .build();
    }
}
