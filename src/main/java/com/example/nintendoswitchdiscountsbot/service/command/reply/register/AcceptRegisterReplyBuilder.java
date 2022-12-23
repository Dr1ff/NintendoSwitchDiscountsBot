package com.example.nintendoswitchdiscountsbot.service.command.reply.register;

import com.example.nintendoswitchdiscountsbot.business.User;
import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.service.command.processor.callback.CallbackCommandData;
import com.example.nintendoswitchdiscountsbot.service.command.reply.RegisterReplyBuilder;
import com.example.nintendoswitchdiscountsbot.service.observer.MessageEventPublisher;
import com.example.nintendoswitchdiscountsbot.service.storage.UserStorageService;
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
public class AcceptRegisterReplyBuilder implements RegisterReplyBuilder {

    private final MessageEventPublisher messageEventPublisher;
    private final UserStorageService userStorageService;


    @Override
    public void build(
            CallbackQuery callbackQuery,
            CallbackCommandData commandData,
            InlineKeyboardMarkup replyMarkup
    ) {
        userStorageService.add(new User(
                callbackQuery.getFrom().getId(),
                commandData.getSubcommandArgs().get(0)
        ));
        var reply = new EditMessageText();
        reply.setChatId(callbackQuery.getMessage().getChatId());
        reply.setMessageId(callbackQuery.getMessage().getMessageId());
        reply.setText(String.format("""
                        Регион %s успешно установлен!
                        Цены на игры в боте будут указаны в валюте выбранного региона.
                        Вы всегда можете изменить его в меню бота.
                        Приступим к работе?""",
                commandData.getSubcommandArgs().get(0) +
                        EmojiManager.getForAlias(
                                commandData
                                        .getSubcommandArgs()
                                        .get(0)
                                        .toLowerCase(Locale.ROOT)
                        ).getUnicode()));
        reply.setReplyMarkup(replyMarkup);
        messageEventPublisher.publish(reply);
    }

    @Override
    public Set<Subcommand> getSubcommand() {
        return Set.of(Subcommand.ACCEPT);
    }
}
