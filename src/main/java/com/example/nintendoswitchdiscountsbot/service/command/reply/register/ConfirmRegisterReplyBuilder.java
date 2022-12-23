package com.example.nintendoswitchdiscountsbot.service.command.reply.register;

import com.example.nintendoswitchdiscountsbot.enums.Country;
import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.service.command.processor.callback.CallbackCommandData;
import com.example.nintendoswitchdiscountsbot.service.command.reply.RegisterReplyBuilder;
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
public class ConfirmRegisterReplyBuilder implements RegisterReplyBuilder {

    private final MessageEventPublisher messageEventPublisher;


    @Override
    public void build(
            CallbackQuery callbackQuery,
            CallbackCommandData commandData,
            InlineKeyboardMarkup replyMarkup
    ) {
        var reply = new EditMessageText();
        reply.setText(getConfirmText(commandData));
        reply.setChatId(callbackQuery.getMessage().getChatId());
        reply.setMessageId(callbackQuery.getMessage().getMessageId());
        reply.setReplyMarkup(replyMarkup);
        messageEventPublisher.publish(reply);
    }

    @Override
    public Set<Subcommand> getSubcommand() {
        return Set.of(Subcommand.CONFIRM);
    }

    public String getConfirmText(CallbackCommandData commandData) {
        var country = Country.valueOf(commandData.getSubcommandArgs().get(0));
        return String.format("Выбранный регион: %s. \nПодтвердите ваш выбор.", country
                + EmojiManager.getForAlias(
                country.name().toLowerCase(Locale.ROOT)).getUnicode());
    }
}
