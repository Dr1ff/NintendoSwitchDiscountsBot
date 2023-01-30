package com.example.nintendoswitchdiscountsbot.enums;

import com.vdurmont.emoji.EmojiParser;
import lombok.Getter;

@Getter
public enum Subcommand {

    BACK(""),
    SELECT(""),
    AFFIRM(""),
    CONFIRM(""),

    CANCEL("❌"),
    PREV("⬅️"),
    NEXT("➡️"),
    ACCEPT("✅"),
    COMPLETE("✅"),

    WISHLIST(":joystick: Мои игры"),
    NOTIFICATION(":mailbox_with_mail: Оповещения"),
    REGION(":earth_americas: Изменить регион"),
    ADD_GAME(":heavy_plus_sign: Добавить игру"),
    REMOVE_GAME(":heavy_minus_sign: Удалить игру");

    private final String buttonText;

    Subcommand(String buttonText) {
        this.buttonText = EmojiParser.parseToUnicode(buttonText);
    }
}
