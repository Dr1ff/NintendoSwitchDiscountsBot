package com.example.nintendoswitchdiscountsbot.service.command.processor.callback.subcommand;

import com.example.nintendoswitchdiscountsbot.business.User;
import com.example.nintendoswitchdiscountsbot.enums.Country;
import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.service.command.processor.callback.CallbackCommandData;
import com.example.nintendoswitchdiscountsbot.service.keyboard.KeyboardService;
import com.example.nintendoswitchdiscountsbot.service.observer.MessageEventPublisher;
import com.example.nintendoswitchdiscountsbot.service.storage.UserStorageService;
import com.vdurmont.emoji.EmojiManager;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.*;

@Component
public class RegisterCallbackSubcommandProcessor implements CallbackSubcommandProcessor {
    private final Map<Subcommand, KeyboardService> keyboardServices;
    private final MessageEventPublisher messageEventPublisher;

    private final UserStorageService userStorageService;

    public RegisterCallbackSubcommandProcessor(List<KeyboardService> keyboardServices, MessageEventPublisher messageEventPublisher, UserStorageService userStorageService) {
        this.messageEventPublisher = messageEventPublisher;
        this.userStorageService = userStorageService;
        Map<Subcommand, KeyboardService> map = new HashMap<>();
        keyboardServices.forEach(keyboardService ->
                keyboardService.getSubcommand().forEach(subcommand -> map.put(subcommand, keyboardService))
        );
        this.keyboardServices = map;
    }

    @Override
    public void process(CallbackQuery callbackQuery, CallbackCommandData commandData) {
        Long chatId = callbackQuery.getMessage().getChatId();
        int messageId = callbackQuery.getMessage().getMessageId();
        Subcommand dataSubcommand = commandData.getSubcommand();

        if(dataSubcommand.equals(Subcommand.ASSERT)) {
            userStorageService.add(new User(
                    chatId,
                    commandData.getSubcommandArgs().get(0)
            ));
            messageEventPublisher.publish(getEditMessageText(
                    String.format("Регион %s успешно установлен!" +
                                    "Цены на игры в боте будут указаны в валюте выбранного региона." +
                                    "\nВы всегда можете изменить его в меню бота",
                            commandData.getSubcommandArgs().get(0) +
                                    EmojiManager.getForAlias(
                                            commandData
                                                    .getSubcommandArgs()
                                                    .get(0)
                                                    .toLowerCase(Locale.ROOT)
                                    ).getUnicode()),
                    chatId,
                    messageId
            ));

        } else if(dataSubcommand.equals(Subcommand.CONFIRM)) {
            var editMessage = getEditMessageText(
                    getConfirmText(
                            Country.valueOf(commandData.getSubcommandArgs().get(0))
                    ),
                    chatId,
                    messageId);
            editMessage.setReplyMarkup(keyboardServices
                    .get(dataSubcommand)
                    .getMarkup(commandData));
            messageEventPublisher.publish(editMessage);
        } else if(dataSubcommand.equals(Subcommand.CANCEL)) {
                var editMessage = getEditMessageText(
                        "Для начала работы выберите регион",
                        chatId,
                        messageId);
                editMessage.setReplyMarkup(keyboardServices
                        .get(dataSubcommand)
                        .getMarkup(commandData));
                messageEventPublisher.publish(editMessage);
        } else {
            messageEventPublisher.publish(getReplyMarkup(
                    chatId,
                    messageId,
                    keyboardServices
                            .get(dataSubcommand)
                            .getMarkup(commandData)
            ));
        }
    }

    private EditMessageReplyMarkup getReplyMarkup(
            Long chatId,
            int messageId,
            InlineKeyboardMarkup replyKeyboard
    ) {
        var replyMarkup = new EditMessageReplyMarkup();
        replyMarkup.setChatId(chatId);
        replyMarkup.setMessageId(messageId);
        replyMarkup.setReplyMarkup(replyKeyboard);
        return replyMarkup;
    }

    private EditMessageText getEditMessageText(
            String text,
            Long chatId,
            Integer messageId
    ) {
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setText(text);
        editMessageText.setChatId(chatId);
        editMessageText.setMessageId(messageId);
        return editMessageText;
    }
    public String getConfirmText(Country country) {
        return String.format("Выбранный регион: %s. \nПодтвердите ваш выбор.", country
                + EmojiManager.getForAlias(
                country.name().toLowerCase(Locale.ROOT)).getUnicode());
    }

    @Override
    public Set<Subcommand> getSubcommand() {
        return Set.of(
                Subcommand.PREV,
                Subcommand.NEXT,
                Subcommand.CONFIRM,
                Subcommand.CANCEL,
                Subcommand.ASSERT
        );
    }
}
