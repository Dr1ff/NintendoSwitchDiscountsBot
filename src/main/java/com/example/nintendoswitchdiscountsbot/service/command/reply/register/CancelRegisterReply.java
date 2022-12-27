package com.example.nintendoswitchdiscountsbot.service.command.reply.register;

import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.service.command.processor.callback.CallbackData;
import com.example.nintendoswitchdiscountsbot.service.command.reply.RegisterReply;
import com.example.nintendoswitchdiscountsbot.service.observer.MessageEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class CancelRegisterReply implements RegisterReply {
    private final MessageEventPublisher messageEventPublisher;

    @Override
    public void reply(
            CallbackQuery callbackQuery,
            CallbackData callbackData,
            InlineKeyboardMarkup replyMarkup
    ) {
        var reply = new EditMessageText();
        reply.setChatId(callbackQuery.getMessage().getChatId());
        reply.setMessageId(callbackQuery.getMessage().getMessageId());
        reply.setText("Для начала работы бота, выберите регион:");
        reply.setReplyMarkup(replyMarkup);
        messageEventPublisher.publish(reply);
    }

    @Override
    public Set<Subcommand> getSubcommand() {
        return Set.of(Subcommand.CANCEL);
    }
}
