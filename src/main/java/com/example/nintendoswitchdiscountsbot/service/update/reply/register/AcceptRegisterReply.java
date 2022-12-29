package com.example.nintendoswitchdiscountsbot.service.update.reply.register;

import com.example.nintendoswitchdiscountsbot.business.User;
import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.business.CallbackData;
import com.example.nintendoswitchdiscountsbot.service.update.processor.callback.subcommand.args.CountrySubcommandArgs;
import com.example.nintendoswitchdiscountsbot.service.update.reply.RegisterReply;
import com.example.nintendoswitchdiscountsbot.service.observer.MessageEventPublisher;
import com.example.nintendoswitchdiscountsbot.service.storage.UserStorageService;
import com.vdurmont.emoji.EmojiManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

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
                )
        );
        messageEventPublisher.publish(
                EditMessageText.builder()
                        .messageId(callbackQuery.getMessage().getMessageId())
                        .chatId(callbackQuery.getMessage().getChatId())
                        .text(
                                String.format(
                                        """
                                                Регион %s успешно установлен!
                                                Цены на игры в боте будут указаны в валюте выбранного региона.
                                                Вы всегда можете изменить его в меню бота.
                                                Приступим к работе?""",
                                        ((CountrySubcommandArgs) callbackData.subcommandArgs()).country() +
                                                EmojiManager.getForAlias(
                                                        ((CountrySubcommandArgs) callbackData.subcommandArgs())
                                                                .country().name()
                                                                .toLowerCase(Locale.ROOT)
                                                ).getUnicode()
                                )
                        )
                        .replyMarkup(replyMarkup)
                        .build()
        );
                        callbackQuery.getFrom().getId(),
                        ((CountrySubcommandArgs) callbackData.subcommandArgs().orElseThrow(
                                () -> new IllegalArgumentException(
                                        "В AcceptKeyboardService попала callbackData " +
                                                "с subcommandArgs = Optional.empty"
                                )
                        )).country().name()
                )
        );
        messageEventPublisher.publish(
                EditMessageText.builder()
                        .messageId(callbackQuery.getMessage().getMessageId())
                        .chatId(callbackQuery.getMessage().getChatId())
                        .text(
                                String.format(
                                        """
                                                Регион %s успешно установлен!
                                                Цены на игры в боте будут указаны в валюте выбранного региона.
                                                Вы всегда можете изменить его в меню бота.
                                                Приступим к работе?""",
                                        ((CountrySubcommandArgs) callbackData.subcommandArgs().get()).country() +
                                                EmojiManager.getForAlias(
                                                        ((CountrySubcommandArgs) callbackData.subcommandArgs().get())
                                                                .country().name()
                                                                .toLowerCase()
                                                ).getUnicode()
                                )
                        )
                        .replyMarkup(replyMarkup)
                        .build()
        );
    }

    @Override
    public Set<Subcommand> getSubcommand() {
        return Set.of(Subcommand.ACCEPT);
    }
}
