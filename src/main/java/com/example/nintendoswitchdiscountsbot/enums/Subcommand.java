package com.example.nintendoswitchdiscountsbot.enums;

import com.vdurmont.emoji.EmojiParser;
import lombok.Getter;

@Getter
public enum Subcommand {

    PREV(":arrow_left:"),
    NEXT(":arrow_right:"),
    CONFIRM(""),
    ACCEPT(":white_check_mark:"),
    CANCEL(":x:"),
    ZERO_RESULT(""),
    ONE_RESULT(""),
    MANY_RESULT(""),
    WISHLIST(":joystick: Мои игры"),
    ADD_GAME(":heavy_plus_sign: Добавить игру"),
    REMOVE_GAME(":heavy_minus_sign: Удалить игру"),
    REGION(":world_map: Изменить регион"),
    NOTIFICATION(":bell: Оповещения");

    private final String buttonText;

    Subcommand(String buttonText) {
        this.buttonText = EmojiParser.parseToUnicode(buttonText);
    }
}
