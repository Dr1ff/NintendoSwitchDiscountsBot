package com.example.nintendoswitchdiscountsbot.service.command.reply.register;

import com.example.nintendoswitchdiscountsbot.business.User;
import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.service.command.processor.callback.CallbackData;
import com.example.nintendoswitchdiscountsbot.service.command.processor.callback.subcommand.CountrySubcommandArgs;
import com.example.nintendoswitchdiscountsbot.service.command.reply.RegisterReply;
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
public class AcceptRegisterReply implements RegisterReply {

    private final MessageEventPublisher messageEventPublisher;
    private final UserStorageService userStorageService;


    @Override
    public void reply(
            CallbackQuery callbackQuery,
            CallbackData callbackData,
            InlineKeyboardMarkup replyMarkup
    ) {
        userStorageService.add(new User(
                callbackQuery.getFrom().getId(),
                ((CountrySubcommandArgs) callbackData.subcommandArgs()).country().name()
        ));
        var reply = new EditMessageText();
        reply.setChatId(callbackQuery.getMessage().getChatId());
        reply.setMessageId(callbackQuery.getMessage().getMessageId());
        reply.setText(String.format("""
                        Регион %s успешно установлен!
                        Цены на игры в боте будут указаны в валюте выбранного региона.
                        Вы всегда можете изменить его в меню бота.
                        Приступим к работе?""",
                ((CountrySubcommandArgs) callbackData.subcommandArgs()).country() +
                        EmojiManager.getForAlias(
                                ((CountrySubcommandArgs) callbackData.subcommandArgs())
                                        .country().name()
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
