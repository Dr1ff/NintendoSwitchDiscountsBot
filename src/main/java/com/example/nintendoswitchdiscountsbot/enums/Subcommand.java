package com.example.nintendoswitchdiscountsbot.enums;

import com.vdurmont.emoji.EmojiParser;
import lombok.Getter;

@Getter
public enum Subcommand {
    PREV(":arrow_left:"),
    NEXT(":arrow_right:"),
    CONFIRM(""),
    ASSERT(":white_check_mark:"),
    CANCEL(":x:");

    private final String buttonText;

    Subcommand(String buttonText) {
        this.buttonText = EmojiParser.parseToUnicode(buttonText);
    }
}
