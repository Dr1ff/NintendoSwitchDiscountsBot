package com.example.nintendoswitchdiscountsbot.service.update.messenger.add_game;

import com.example.nintendoswitchdiscountsbot.business.CallbackData;
import com.example.nintendoswitchdiscountsbot.business.Game;
import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.service.observer.MessageEventPublisher;
import com.example.nintendoswitchdiscountsbot.service.storage.GameStorageService;
import com.example.nintendoswitchdiscountsbot.service.storage.UserStorageService;
import com.example.nintendoswitchdiscountsbot.service.update.processor.callback.subcommand.args.integer.IntegerSubcommandArgs;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Component
@RequiredArgsConstructor
public class SecondStepAddGameMessenger extends AddGameMessenger {

    private final UserStorageService userStorageService;
    private final GameStorageService gameStorageService;
    private final MessageEventPublisher messageEventPublisher;

    @Override
    public Set<Subcommand> getSubcommand() {
        return Set.of(Subcommand.COMPLETE);
    }

    @Override
    public Command getCommand() {
        return null;
    }

    public void reply(CallbackQuery callbackQuery, CallbackData callbackData, InlineKeyboardMarkup replyMarkup) {
        var subcommandArgs = ((IntegerSubcommandArgs) callbackData.subcommandArgs().orElseThrow(
                () -> new IllegalArgumentException(
                        "В AddGameSecondStepReply попала CallbackData " +
                                "c SubcommandArgs = Optional.empty()"
                )
        ));
        addGameInWishlist(
                callbackQuery.getMessage().getChatId(),
                getGame(subcommandArgs)
        );
        messageEventPublisher.publish(
                EditMessageText.builder()
                        .chatId(callbackQuery.getMessage().getChatId())
                        .replyMarkup(replyMarkup)
                        .messageId(callbackQuery.getMessage().getMessageId())
                        .text(String.format(
                                        "Игра %s \nДобавлена в ваш список для отслеживания скидок",
                                        getGame(subcommandArgs).name()
                                )
                        )
                        .build()
        );
    }

    private Game getGame(IntegerSubcommandArgs subcommandArgs) {
        return gameStorageService
                .findByHashcode(subcommandArgs.integer())
                .orElseThrow(
                        () -> new IllegalArgumentException(
                                "В AddGameSecondStepReply попала CallbackData " +
                                        "c SubcommandArgs по которой нет совпадений в таблице game"
                        )
                );
    }

    private void addGameInWishlist(Long chatId, Game game) {
        userStorageService.addGameInWishlist(chatId, game);
    }
}
