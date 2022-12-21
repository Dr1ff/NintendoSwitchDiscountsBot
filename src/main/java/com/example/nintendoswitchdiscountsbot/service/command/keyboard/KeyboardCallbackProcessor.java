package com.example.nintendoswitchdiscountsbot.service.command.keyboard;

import com.example.nintendoswitchdiscountsbot.business.User;
import com.example.nintendoswitchdiscountsbot.enums.Country;
import com.example.nintendoswitchdiscountsbot.service.command.CallbackProcessor;
import com.example.nintendoswitchdiscountsbot.service.observer.MessageEventPublisher;
import com.example.nintendoswitchdiscountsbot.service.storage.UserStorageService;
import com.vdurmont.emoji.EmojiManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class KeyboardCallbackProcessor implements CallbackProcessor {
    private final KeyboardService keyboardService;
    private final UserStorageService userStorageService;
    private final MessageEventPublisher messageEventPublisher;
    public void process(CallbackQuery callbackQuery) {
       String data = callbackQuery.getData();
       Long chatId = callbackQuery.getMessage().getChatId();
       Integer messageId = callbackQuery.getMessage().getMessageId();

       EditMessageReplyMarkup replyMarkup = new EditMessageReplyMarkup();
       EditMessageText editMessageText = new EditMessageText();

       editMessageText.setChatId(chatId);
       replyMarkup.setChatId(chatId);
       editMessageText.setMessageId(messageId);
       replyMarkup.setMessageId(messageId);

       if(data.equals("cancel") || data.equals("1")||data.equals("2")||data.equals("3")||data.equals("4")) {
           replyMarkup.setReplyMarkup(keyboardService.getKeyboard(data));
           messageEventPublisher.publish(replyMarkup);
       } else if(data.contains("/")) {
           data = data.replace("/", "");
           userStorageService.add(new User(chatId, data));
           editMessageText.setText(String.format("Регион %s успешно установлен!Цены будут указаны в %S" +
                   "\nВы всегда можете изменить его в меню бота",
                   data.replace("/", "")
                           + EmojiManager.getForAlias(data
                           .toLowerCase(Locale.ROOT)).getUnicode(),
                           Country.valueOf(data).getCurrency()
                           + Country.valueOf(data).getSign()));
           messageEventPublisher.publish(editMessageText);
       } else {
           editMessageText.setReplyMarkup(keyboardService.getKeyboard(data));
           editMessageText.setText(String.format("Выбранный регион: %s. \nПодтвердите ваш выбор.", data
                   + EmojiManager.getForAlias(data.toLowerCase(Locale.ROOT)).getUnicode()));
           messageEventPublisher.publish(editMessageText);
       }

    }
}
