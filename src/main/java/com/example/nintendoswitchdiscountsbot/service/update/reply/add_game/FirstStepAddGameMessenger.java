package com.example.nintendoswitchdiscountsbot.service.update.reply.add_game;

import com.example.nintendoswitchdiscountsbot.business.CallbackData;
import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.service.observer.MessageEventPublisher;
import com.example.nintendoswitchdiscountsbot.service.storage.UserStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class FirstStepAddGameMessenger extends AddGameMessenger {

    private final MessageEventPublisher messageEventPublisher;
    private final UserStorageService userStorageService;

    @Override
    public Set<Subcommand> getSubcommand() {
        return Set.of(Subcommand.ADD_GAME, Subcommand.BACK);
    }

    @Override
    public void reply(CallbackQuery callbackQuery, CallbackData callbackData, InlineKeyboardMarkup replyMarkup) {
        messageEventPublisher.publish(
                DeleteMessage.builder()
                        .chatId(callbackQuery.getMessage().getChatId())
                        .messageId(callbackQuery.getMessage().getMessageId())
                        .build()
        );
        reply(callbackQuery.getMessage().getChatId(), replyMarkup);
    }

    @Override
    public Command getCommand() {
        return Command.ADD_GAME;
    }

    public void reply(Long chatId, InlineKeyboardMarkup replyMarkup) {
        setUserState(chatId);
        messageEventPublisher.publish(
                SendMessage.builder()
                        .text(
                                "Для того чтобы добавить " +
                                        "игру в список отслеживания, " +
                                        "пришлите мне ее название."

                        )
                        .replyMarkup(replyMarkup)
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
                        .state(Command.ADD_GAME)
                        .build()
        );
    }
}
