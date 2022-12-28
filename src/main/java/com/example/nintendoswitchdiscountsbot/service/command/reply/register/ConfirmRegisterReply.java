package com.example.nintendoswitchdiscountsbot.service.command.reply.register;

import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.service.command.processor.callback.CallbackData;
import com.example.nintendoswitchdiscountsbot.service.command.processor.callback.subcommand.CountrySubcommandArgs;
import com.example.nintendoswitchdiscountsbot.service.command.reply.RegisterReply;
import com.example.nintendoswitchdiscountsbot.service.observer.MessageEventPublisher;
import com.vdurmont.emoji.EmojiManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.Locale;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ConfirmRegisterReply implements RegisterReply {

    private final MessageEventPublisher messageEventPublisher;

    @Override
    public void reply(
            CallbackQuery callbackQuery,
            CallbackData callbackData,
            InlineKeyboardMarkup replyMarkup
    ) {
        messageEventPublisher.publish(
                EditMessageText.builder()
                        .messageId(callbackQuery.getMessage().getMessageId())
                        .chatId(callbackQuery.getMessage().getChatId())
                        .text(getConfirmText(callbackData))
                        .replyMarkup(replyMarkup)
                        .build()
        );
    }

    @Override
    public Set<Subcommand> getSubcommand() {
        return Set.of(Subcommand.CONFIRM);
    }

    public String getConfirmText(CallbackData callbackData) {
        var country = (((CountrySubcommandArgs) callbackData.subcommandArgs().orElseThrow(
                () -> new IllegalArgumentException(
                        "В ConfirmRegisterReply попала callbackData " +
                                "с subcommandArgs = Optional.empty"
                )
        ))
                .country());
        return String.format(
                "Выбранный регион: %s. \nПодтвердите ваш выбор.",
                country + EmojiManager
                        .getForAlias(country.name().toLowerCase(Locale.ROOT))
                        .getUnicode()
        );
    }
}
