package com.example.nintendoswitchdiscountsbot.service.keyboard.wishlist;

import com.example.nintendoswitchdiscountsbot.business.CallbackData;
import com.example.nintendoswitchdiscountsbot.business.Game;
import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.repository.RepositorySearchException;
import com.example.nintendoswitchdiscountsbot.service.storage.GameStorageService;
import com.example.nintendoswitchdiscountsbot.service.update.processor.callback.subcommand.args.integer.IntegerSubcommandArgs;
import com.example.nintendoswitchdiscountsbot.service.utils.KeyboardHelper;
import com.vdurmont.emoji.EmojiParser;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Component
@RequiredArgsConstructor
public class WishlistGameShowKeyboardService implements WishlistKeyboardService {

    private final static int NUMBER_OF_ROWS = 2;
    private final static int DELETE_BUTTON_ROW_INDEX = 0;
    private final static int BACK_BUTTON_ROW_INDEX = 1;
    private final static int GAMES_BUTTONS_IN_WISHLIST_KEYBOARD = 5;

    private final KeyboardHelper keyboardHelper;
    private final GameStorageService gameStorageService;

    @Override
    public InlineKeyboardMarkup getMarkup(CallbackData callbackData) {
        return new InlineKeyboardMarkup();
    }

    @Override
    public InlineKeyboardMarkup getWishlistMarkup(CallbackData callbackData, List<Game> wishlist) {
        var rows = keyboardHelper.getRows(NUMBER_OF_ROWS);
        rows.get(DELETE_BUTTON_ROW_INDEX).add(
                keyboardHelper.getButton(
                        "➖ Удалить игру",
                        callbackData.toBuilder()
                                .subcommand(Optional.of(Subcommand.AFFIRM))
                                .build()
                )
        );
        rows.get(BACK_BUTTON_ROW_INDEX).add(
                keyboardHelper.getButton(
                        EmojiParser.parseToUnicode(":back: Назад к списку"),
                        callbackData.toBuilder()
                                .subcommand(Optional.of(Subcommand.BACK))
                                .subcommandArgs(Optional.of(
                                        new IntegerSubcommandArgs(getBackPage(callbackData, wishlist)))
                                )
                                .build()
                )
        );
        return new InlineKeyboardMarkup(rows);
    }

    @Override
    public Set<Subcommand> getSubcommands() {
        return Set.of(Subcommand.SHOW, Subcommand.CANCEL);
    }

    @Override
    public Set<Command> getCommands() {
        return Set.of(Command.WISHLIST);
    }

    private Integer getBackPage(CallbackData callbackData, List<Game> wishlist) {
        return wishlist.indexOf(
                gameStorageService.findByHashcode(
                        callbackData
                                .subcommandArgs()
                                .orElseThrow(
                                        () -> new IllegalArgumentException(
                                                "В WishlistGameShowKeyboardService попала callbackData " +
                                                        "с subcommandArgs = Optional.empty")
                                )
                                .hashCode()
                ).orElseThrow(
                        () -> new RepositorySearchException(
                                "Игра с таким hashcode не найдена в репозитории")
                )
        ) / GAMES_BUTTONS_IN_WISHLIST_KEYBOARD * GAMES_BUTTONS_IN_WISHLIST_KEYBOARD;
    }
}