package com.example.nintendoswitchdiscountsbot.service.command.reply.register;

import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.service.command.processor.callback.CallbackCommandData;
import com.example.nintendoswitchdiscountsbot.service.command.reply.RegisterReplyBuilder;
import com.example.nintendoswitchdiscountsbot.service.observer.MessageEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class CountryRegisterReplyBuilder implements RegisterReplyBuilder {

    private final MessageEventPublisher messageEventPublisher;

    @Override
    public void build(
            CallbackQuery callbackQuery,
            CallbackCommandData commandData,
            InlineKeyboardMarkup replyMarkup
    ) {
        var reply = new EditMessageReplyMarkup();
        reply.setChatId(callbackQuery.getMessage().getChatId());
        reply.setMessageId(callbackQuery.getMessage().getMessageId());
        reply.setReplyMarkup(replyMarkup);
        messageEventPublisher.publish(reply);
    }

    @Override
    public Set<Subcommand> getSubcommand() {
        return Set.of(Subcommand.NEXT, Subcommand.PREV);
    }
}
