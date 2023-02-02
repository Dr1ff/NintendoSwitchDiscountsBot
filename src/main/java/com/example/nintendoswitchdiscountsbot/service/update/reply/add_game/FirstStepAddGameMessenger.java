package com.example.nintendoswitchdiscountsbot.service.update.reply.add_game;

import com.example.nintendoswitchdiscountsbot.business.CallbackData;
import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.service.observer.MessageEventPublisher;
import com.example.nintendoswitchdiscountsbot.service.storage.UserStorageService;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Component
@RequiredArgsConstructor
public class FirstStepAddGameMessenger extends AddGameMessenger {

    private final MessageEventPublisher messageEventPublisher;
    private final UserStorageService userStorageService;

    @Override
    public Set<Subcommand> getSubcommand() {
        return Set.of(
                Subcommand.G_ADD,
                Subcommand.CANCEL,
                Subcommand.BACK
        );
    }

    @Override
    public void reply(CallbackQuery callbackQuery, CallbackData callbackData, InlineKeyboardMarkup replyMarkup) {
        reply(
                callbackQuery.getMessage().getChatId(),
                callbackQuery.getMessage().getMessageId(),
                replyMarkup
        );
    }

    @Override
    public Command getCommand() {
        return Command.G_ADD;
    }

    public void reply(
            Long chatId,
            Integer messageId,
            InlineKeyboardMarkup replyMarkup
    ) {
        setUserState(chatId);
        messageEventPublisher.publish(
                EditMessageText.builder()
                        .text(
                                "Для того чтобы добавить " +
                                        "игру в список отслеживания, " +
                                        "пришлите мне ее название."

                        )
                        .replyMarkup(replyMarkup)
                        .messageId(messageId)
                        .chatId(chatId)
                        .build()
        );
    }

    private void setUserState(Long chatId) {
        userStorageService.add(
                userStorageService
                        .findById(chatId).orElseThrow(
                                () -> new RuntimeException("")
                        )
                        .toBuilder()
                        .state(Command.G_ADD)
                        .build()
        );
    }
}
