package com.example.nintendoswitchdiscountsbot.service.update.reply.menu;

import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.service.observer.MessageEventPublisher;
import com.vdurmont.emoji.EmojiParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class AcceptMenuMessenger extends MenuMessenger {

    private final MessageEventPublisher messageEventPublisher;

    @Override
    public Set<Subcommand> getSubcommand() {
        return Set.of(Subcommand.ACCEPT);
    }

    @Override
    public void reply(Long chatId, InlineKeyboardMarkup replyMarkup) {
        messageEventPublisher.publish(
                SendMessage.builder()
                        .chatId(chatId)
                        .text(EmojiParser.parseToUnicode(
                                """
                                        🎮👾🍄🧙💎МЕНЮ🧝❤️❤️❤️💛
                                        """
                        ))
                        .replyMarkup(replyMarkup)
                        .build()
        );
    }

    @Override
    public void reply(
            CallbackQuery callbackQuery,
            InlineKeyboardMarkup replyMarkup
    ) {
        messageEventPublisher.publish(
                DeleteMessage.builder()
                        .chatId(callbackQuery.getMessage().getChatId())
                        .messageId(callbackQuery.getMessage().getMessageId())
                        .build()
        );
        reply(callbackQuery.getMessage().getChatId(), replyMarkup);
    }
}
