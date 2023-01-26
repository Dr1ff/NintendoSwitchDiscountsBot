package com.example.nintendoswitchdiscountsbot.service.update.keyboard;

import com.example.nintendoswitchdiscountsbot.business.CallbackData;
import com.example.nintendoswitchdiscountsbot.service.update.processor.callback.subcommand.args.number.NumSubcommandArgs;
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
                ((NumSubcommandArgs) callbackData
                        .subcommandArgs()
                        .orElseThrow(() -> new IllegalArgumentException(
                                        "callbackData с subcommandArgs = Optional.empty"
                                )
                        )).num()
        );
    }
}
