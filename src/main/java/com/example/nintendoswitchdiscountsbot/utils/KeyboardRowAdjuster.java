package com.example.nintendoswitchdiscountsbot.utils;

import com.example.nintendoswitchdiscountsbot.business.CallbackData;
import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.service.update.processor.callback.subcommand.args.integer.IntegerSubcommandArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class KeyboardRowAdjuster {

    private final KeyboardHelper keyboardHelper;

    public void setPaginationButtons(
            List<List<InlineKeyboardButton>> rows,
            int buttonsOnPage,
            int buttonsListSize,
            int firstButtonListIndex,
            int paginationRowIndex,
            CallbackData callbackData
    ) {
        int firstButtonIndex = PaginationKeyboardUtil.firstButtonIndex(callbackData);
        int nextKeyboardFirstButtonIndex = PaginationKeyboardUtil
                .nextKeyboardFirstIndex(firstButtonIndex, buttonsOnPage);
        int prevKeyboardFirstButtonIndex = PaginationKeyboardUtil.
                prevKeyboardFirstIndex(firstButtonIndex, buttonsOnPage);
        if (PaginationKeyboardUtil.isFirstPage(firstButtonListIndex)) {
            rows.get(paginationRowIndex).add(keyboardHelper.getEmpty());
        } else {
            rows.get(paginationRowIndex).add(keyboardHelper.getButton(
                            callbackData.toBuilder()
                                    .subcommand(Optional.of(Subcommand.PREV))
                                    .subcommandArgs(Optional.of(new IntegerSubcommandArgs(prevKeyboardFirstButtonIndex)))
                                    .build()
                    )
            );
        }
        if (PaginationKeyboardUtil.isLastPage(
                PaginationKeyboardUtil.nextKeyboardFirstIndex(
                        firstButtonListIndex,
                        buttonsOnPage

                ),
                buttonsListSize
        )) {
            rows.get(paginationRowIndex).add(keyboardHelper.getEmpty());
        } else {
            rows.get(paginationRowIndex).add(keyboardHelper.getButton(
                            callbackData.toBuilder()
                                    .subcommand(Optional.of(Subcommand.NEXT))
                                    .subcommandArgs(Optional.of(new IntegerSubcommandArgs(nextKeyboardFirstButtonIndex)))
                                    .build()
                    )
            );
        }
    }
    public void assembleRows(
            List<List<InlineKeyboardButton>> rows,
            Iterator<InlineKeyboardButton> buttonIterator,
            int numberOfElementsRows,
            int buttonsInRow
    ) {
        for (int i = 0; i < numberOfElementsRows; i++) {
            assembleRow(
                    rows.get(i),
                    buttonIterator,
                    buttonsInRow
            );
        }
    }

    private void assembleRow(
            List<InlineKeyboardButton> row,
            Iterator<InlineKeyboardButton> iterator,
            int buttonsInRow
    ) {
        for (int i = 0; i < buttonsInRow && iterator.hasNext(); i++) {
            row.add(iterator.next());
        }
    }

}
