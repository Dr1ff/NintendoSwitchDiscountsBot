package com.example.nintendoswitchdiscountsbot.enums;

import com.vdurmont.emoji.EmojiParser;
import lombok.Getter;

@Getter
public enum Subcommand {

    BACK(""),
    SELECT(""),
    AFFIRM(""),
    CONFIRM(""),

    CANCEL(":x:"),
    PREV(":arrow_left:"),
    NEXT(":arrow_right:"),
    ACCEPT(":white_check_mark:"),
    COMPLETE(":white_check_mark:"),

    WISHLIST(":joystick: Мои игры"),
    NOTIFICATION(":bell: Оповещения"),
    REGION(":world_map: Изменить регион"),
    ADD_GAME(":heavy_plus_sign: Добавить игру"),
    REMOVE_GAME(":heavy_minus_sign: Удалить игру");

    private final String buttonText;

    Subcommand(String buttonText) {
        this.buttonText = EmojiParser.parseToUnicode(buttonText);
    }
}
