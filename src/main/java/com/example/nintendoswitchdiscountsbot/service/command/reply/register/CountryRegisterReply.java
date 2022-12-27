package com.example.nintendoswitchdiscountsbot.service.command.reply.register;

import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.service.command.processor.callback.CallbackData;
import com.example.nintendoswitchdiscountsbot.service.command.reply.RegisterReply;
import com.example.nintendoswitchdiscountsbot.service.observer.MessageEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class CountryRegisterReply implements RegisterReply {

    private final MessageEventPublisher messageEventPublisher;

    @Override
    public void reply(
            CallbackQuery callbackQuery,
            CallbackData callbackData,
            InlineKeyboardMarkup replyMarkup
    ) {
        messageEventPublisher.publish(
                EditMessageReplyMarkup.builder()
                        .messageId(callbackQuery.getMessage().getMessageId())
                        .chatId(callbackQuery.getMessage().getChatId())
                        .replyMarkup(replyMarkup)
                        .build()
        );
    }

    @Override
    public Set<Subcommand> getSubcommand() {
        return Set.of(Subcommand.NEXT, Subcommand.PREV);
    }
}
