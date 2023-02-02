package com.example.nintendoswitchdiscountsbot.enums;

import com.vdurmont.emoji.EmojiParser;
import lombok.Getter;

@Getter
public enum Subcommand {

    SHOW(""),
    SELECT(""),
    AFFIRM(""),
    CONFIRM(""),

    CANCEL("❌"),
    ACCEPT("✅"),
    PREV("⬅️"),
    NEXT("➡️"),
    COMPLETE("✅"),
    BACK(":back:"),

    WISHLIST(":joystick: Мои игры"),
    G_ADD(":heavy_plus_sign: Добавить игру"),
    REGION(":earth_americas: Изменить регион"),
    NOTIFICATION(":mailbox_with_mail: Оповещения");

    private final String buttonText;

    Subcommand(String buttonText) {
        this.buttonText = EmojiParser.parseToUnicode(buttonText);
    }
}
