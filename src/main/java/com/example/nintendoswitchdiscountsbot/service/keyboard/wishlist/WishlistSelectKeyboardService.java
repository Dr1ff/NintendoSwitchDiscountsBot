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

    private final static int NUM_NAV_ROWS = 3;
    private final static int NUM_GAMES_BTNS = 5;
    private final static int ADD_GAME_BTN_INDEX = 6;
    private final static int PAGINATION_ROW_INDEX = 5;
    private final static int BACK_ROW_INDEX = 7;
    private final static int GAMES_BTNS_IN_ROW = 1;


    private final KeyboardRowAdjuster keyboardRowAdjuster;
    private final KeyboardHelper keyboardHelper;

    @Override
    public InlineKeyboardMarkup getMarkup(CallbackData callbackData) {
        return new InlineKeyboardMarkup();
    }

    @Override
    public InlineKeyboardMarkup getWishlistMarkup(CallbackData callbackData, List<Game> wishlist) {
        var rows = keyboardHelper.getRows(NUM_GAMES_BTNS + NUM_NAV_ROWS);
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
        keyboardRowAdjuster.setPaginationButtons(
                rows,
                NUM_GAMES_BTNS,
                wishlist.size(),
                getCurrentFirstBtnIndex(callbackData),
                PAGINATION_ROW_INDEX,
                callbackData
        );
        keyboardRowAdjuster.assembleRows(
                rows,
                games.subList(
                                getCurrentFirstBtnIndex(callbackData),
                                getNextFirstBtnIndex(
                                        getCurrentFirstBtnIndex(callbackData),
                                        wishlist.size()
                                )
                        )
                        .iterator(),
                NUM_GAMES_BTNS,
                GAMES_BTNS_IN_ROW
        );
        rows.get(ADD_GAME_BTN_INDEX).add(
                keyboardHelper.getButton(
                        new CallbackData(
                                Command.G_ADD,
                                Optional.of(Subcommand.G_ADD),
                                Optional.empty(),
                                Optional.empty()
                        )
                )
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

    private Integer getCurrentFirstBtnIndex(CallbackData callbackData) {
        return PaginationKeyboardUtil.firstButtonIndex(callbackData);
    }

    private Integer getNextFirstBtnIndex(int firstBtnIndex, int wishlistSize) {
        int nextFirstBtnIndex =
                PaginationKeyboardUtil.nextKeyboardFirstIndex(firstBtnIndex, NUM_GAMES_BTNS);
        if (
                PaginationKeyboardUtil.isLastPage(
                        nextFirstBtnIndex,
                        NUM_GAMES_BTNS
                )
        ) {
            nextFirstBtnIndex = wishlistSize;
        }
        return nextFirstBtnIndex;
    }
}
