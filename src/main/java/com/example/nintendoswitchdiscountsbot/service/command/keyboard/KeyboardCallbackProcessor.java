package com.example.nintendoswitchdiscountsbot.service.command.keyboard;

import com.example.nintendoswitchdiscountsbot.dto.User;
import com.example.nintendoswitchdiscountsbot.service.MessageEventPublisher;
import com.example.nintendoswitchdiscountsbot.service.storage.UserStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class KeyboardCallbackProcessor {
    private final KeyboardService keyboardService;
    private final UserStorageService userStorageService;
    private final MessageEventPublisher messageEventPublisher;
    public void process(Update update) {
       String data = update.getCallbackQuery().getData();
       Long chatId = update.getCallbackQuery().getMessage().getChatId();
       Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
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
           userStorageService.add(new User(chatId, data.replace("/", "")));
           editMessageText.setText(String.format("Регион %s успешно установлен!" +
                   "\nВы всегда можете изменить его в меню бота", data.replace("/", "")));
           messageEventPublisher.publish(editMessageText);
       } else {
           editMessageText.setReplyMarkup(keyboardService.getKeyboard(data));
           editMessageText.setText(String.format("Выбранный регион: %s. \nПодтвердите ваш выбор.", data));
           messageEventPublisher.publish(editMessageText);
       }

    }
}
