package com.example.nintendoswitchdiscountsbot.service.update.processor.callback.subcommand;

import com.example.nintendoswitchdiscountsbot.business.CallbackData;
import com.example.nintendoswitchdiscountsbot.business.Game;
import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.repository.RepositorySearchException;
import com.example.nintendoswitchdiscountsbot.service.keyboard.wishlist.WishlistKeyboardService;
import com.example.nintendoswitchdiscountsbot.service.storage.GameStorageService;
import com.example.nintendoswitchdiscountsbot.service.storage.UserStorageService;
import com.example.nintendoswitchdiscountsbot.service.update.messenger.wishlist.WishlistMessenger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Component
public class WishlistCallbackSubcommandProcessor implements CallbackSubcommandProcessor {

    private final WishlistMessenger wishlistMessenger;
    private final Map<Subcommand, WishlistKeyboardService> keyboardServices;
    private final UserStorageService userStorageService;
    private final GameStorageService gameStorageService;

    public WishlistCallbackSubcommandProcessor(
            WishlistMessenger wishlistMessenger,
            List<WishlistKeyboardService> keyboardServices,
            UserStorageService userStorageService,
            GameStorageService gameStorageService
    ) {
        this.wishlistMessenger = wishlistMessenger;
        this.userStorageService = userStorageService;
        this.gameStorageService = gameStorageService;
        this.keyboardServices = new HashMap<>();
        keyboardServices
                .stream()
                .filter(service -> service.getCommands().contains(getCommand()))
                .forEach(keyboardService -> keyboardService.getSubcommands()
                        .forEach(subcommand ->
                                this.keyboardServices.put(subcommand, keyboardService)));
    }

    @Override
    public void process(CallbackQuery callbackQuery, CallbackData callbackData) {
        var subcommand = callbackData.subcommand().orElseThrow();
        var chatId = callbackQuery.getMessage().getChatId();
        var messageId = callbackQuery.getMessage().getMessageId();
        if (subcommand.equals(Subcommand.COMPLETE)) {
            removeGame(chatId, getGame(callbackData));
            wishlistMessenger.gameRemoveReply(
                    getGame(callbackData),
                    chatId,
                    messageId,
                    keyboardServices.get(subcommand).getMarkup(callbackData)
            );
        } else if (subcommand.equals(Subcommand.SHOW) || subcommand.equals(Subcommand.CANCEL)) {
            wishlistMessenger.gameShowReply(
                    getGame(callbackData),
                    chatId,
                    messageId,
                    keyboardServices
                            .get(subcommand)
                            .getWishlistMarkup(
                                    callbackData,
                                    getWishlist(callbackQuery
                                    )
                            )
            );
        } else if (subcommand.equals(Subcommand.AFFIRM)) {
            wishlistMessenger.affirmReply(
                    getGame(callbackData),
                    chatId,
                    messageId,
                    keyboardServices
                            .get(subcommand)
                            .getMarkup(callbackData)
            );
        } else {
            wishlistMessenger.wishlistReply(
                    chatId,
                    messageId,
                    keyboardServices
                            .get(subcommand)
                            .getWishlistMarkup(
                                    callbackData,
                                    getWishlist(callbackQuery)
                            )
            );
        }
    }

    @Override
    public Set<Subcommand> getSubcommands() {
        return Set.of(
                Subcommand.COMPLETE,
                Subcommand.WISHLIST,
                Subcommand.AFFIRM,
                Subcommand.CANCEL,
                Subcommand.BACK,
                Subcommand.PREV,
                Subcommand.NEXT,
                Subcommand.SHOW
        );
    }

    @Override
    public Command getCommand() {
        return Command.WISHLIST;
    }

    private void removeGame(Long chatId, Game game) {
        userStorageService.removeGameAtWishlist(chatId, game);
    }

    private Game getGame(CallbackData callbackData) {
        return gameStorageService
                .findByHashcode(
                        callbackData.subcommandArgs()
                                .orElseThrow(
                                        () -> new IllegalArgumentException(
                                                "В WishlistCallbackSubcommandProcessor попала callbackData " +
                                                        "с subcommandArgs = Optional.empty")
                                )
                                .hashCode()
                )
                .orElseThrow(
                        () -> new RepositorySearchException(
                                "В GameRepository не найдена игра по hashcode")
                );
    }

    private List<Game> getWishlist(CallbackQuery callbackQuery) {
        return userStorageService.findById(
                        callbackQuery.getMessage().getChatId()
                )
                .orElseThrow(
                        () -> new RepositorySearchException(
                                "В UserRepository не найден user с таким id")
                )
                .wishlist();
    }
}
