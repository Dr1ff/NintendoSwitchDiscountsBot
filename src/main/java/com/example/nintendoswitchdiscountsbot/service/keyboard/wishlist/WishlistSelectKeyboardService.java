package com.example.nintendoswitchdiscountsbot.service.keyboard.wishlist;

import com.example.nintendoswitchdiscountsbot.business.CallbackData;
import com.example.nintendoswitchdiscountsbot.business.Game;
import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.service.update.processor.callback.subcommand.args.integer.IntegerSubcommandArgs;
import com.example.nintendoswitchdiscountsbot.service.utils.KeyboardHelper;
import com.example.nintendoswitchdiscountsbot.service.utils.KeyboardRowAdjuster;
import com.example.nintendoswitchdiscountsbot.service.utils.PaginationKeyboardUtil;
import com.vdurmont.emoji.EmojiParser;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Component
@RequiredArgsConstructor
public class WishlistSelectKeyboardService implements WishlistKeyboardService {

    private final static int NUMBER_OF_NAVIGATION_ROWS = 2;
    private final static int NUMBER_OF_GAMES_BUTTONS = 8;
    private final static int PAGINATION_ROW_INDEX = 8;
    private final static int BUTTONS_IN_ROW = 1;
    private final static int BACK_ROW_INDEX = 9;


    private final KeyboardRowAdjuster keyboardRowAdjuster;
    private final KeyboardHelper keyboardHelper;

    @Override
    public InlineKeyboardMarkup getMarkup(CallbackData callbackData) {
        return new InlineKeyboardMarkup();
    }

    @Override
    public InlineKeyboardMarkup getWishlistMarkup(CallbackData callbackData, List<Game> wishlist) {
        var rows = keyboardHelper.getRows(
                NUMBER_OF_GAMES_BUTTONS + NUMBER_OF_NAVIGATION_ROWS
        );
        var games = wishlist.stream()
                .map(game -> keyboardHelper.getButton(
                                game.name(),
                                callbackData.toBuilder()
                                        .subcommand(Optional.of(Subcommand.SHOW))
                                        .subcommandArgs(Optional.of(
                                                new IntegerSubcommandArgs(game.hashcode())
                                        ))
                                        .build()
                        )
                )
                .toList();
        int firstButtonIndex = PaginationKeyboardUtil.firstButtonIndex(callbackData);
        int nextKeyboardFirstButtonIndex =
                PaginationKeyboardUtil.nextKeyboardFirstIndex(firstButtonIndex, NUMBER_OF_GAMES_BUTTONS);
        keyboardRowAdjuster.setPaginationButtons(
                rows,
                NUMBER_OF_GAMES_BUTTONS,
                wishlist.size(),
                firstButtonIndex,
                PAGINATION_ROW_INDEX,
                callbackData
        );
        if (
                PaginationKeyboardUtil.isLastPage(
                        nextKeyboardFirstButtonIndex,
                        NUMBER_OF_GAMES_BUTTONS
                )
        ) {
            nextKeyboardFirstButtonIndex = wishlist.size();
        }
        keyboardRowAdjuster.assembleRows(
                rows,
                games.subList(
                                firstButtonIndex, nextKeyboardFirstButtonIndex
                        )
                        .iterator(),
                NUMBER_OF_GAMES_BUTTONS,
                BUTTONS_IN_ROW
        );
        rows.get(BACK_ROW_INDEX).add(
                keyboardHelper.getButton(
                        EmojiParser.parseToUnicode(":back: Назад в меню"),
                        callbackData.toBuilder()
                                .command(Command.MENU)
                                .subcommand(Optional.of(Subcommand.SHOW))
                                .subcommandArgs(Optional.empty())
                                .build()
                )
        );
        return new InlineKeyboardMarkup(rows);
    }

    @Override
    public Set<Subcommand> getSubcommands() {
        return Set.of(
                Subcommand.WISHLIST,
                Subcommand.BACK,
                Subcommand.PREV,
                Subcommand.NEXT
        );
    }

    @Override
    public Set<Command> getCommands() {
        return Set.of(Command.WISHLIST);
    }
}
