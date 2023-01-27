package com.example.nintendoswitchdiscountsbot.utils;

import com.example.nintendoswitchdiscountsbot.business.CallbackData;
import com.example.nintendoswitchdiscountsbot.service.update.processor.callback.subcommand.args.integer.IntegerSubcommandArgs;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PaginationKeyboardUtil {

    public int nextKeyboardFirstIndex(int index, int numOfButtons) {
        return index + numOfButtons;
    }

    public int prevKeyboardFirstIndex(int index, int numOfButtons) {
        return index - numOfButtons;
    }

    public boolean isLastPage(int index, int listSize) {
        return index > listSize - 1;
    }

    public boolean isFirstPage(int index) {
        return index <= 0;
    }

    public int firstButtonIndex(CallbackData callbackData) {
        return (
                ((IntegerSubcommandArgs) callbackData
                        .subcommandArgs()
                        .orElseThrow(() -> new IllegalArgumentException(
                                        "callbackData с subcommandArgs = Optional.empty"
                                )
                        )).integer()
        );
    }
}
