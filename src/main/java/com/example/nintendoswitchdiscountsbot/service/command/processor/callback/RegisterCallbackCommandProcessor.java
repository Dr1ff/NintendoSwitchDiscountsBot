package com.example.nintendoswitchdiscountsbot.service.command.processor.callback;

import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.service.command.processor.callback.subcommand.CallbackSubcommandProcessor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RegisterCallbackCommandProcessor implements CallbackCommandProcessor {
    private final Map<Subcommand, CallbackSubcommandProcessor> processors;

    public RegisterCallbackCommandProcessor(List<CallbackSubcommandProcessor> processors) {
        Map<Subcommand, CallbackSubcommandProcessor> map = new HashMap<>();
        processors.forEach(processor ->
                processor.getSubcommand().forEach(subcommand -> map.put(subcommand, processor))
        );
        this.processors = map;
    }

    public void process(CallbackQuery callbackQuery, CallbackCommandData commandData) {
         processors.get(commandData.getSubcommand()).process(callbackQuery, commandData);

//       Integer messageId = callbackQuery.getMessage().getMessageId();
//
//       EditMessageReplyMarkup replyMarkup = new EditMessageReplyMarkup();
//       EditMessageText editMessageText = new EditMessageText();
//
//       editMessageText.setChatId(chatId);
//       replyMarkup.setChatId(chatId);
//       editMessageText.setMessageId(messageId);
//       replyMarkup.setMessageId(messageId);
//
//       if(data.equals("cancel") || data.equals("1")||data.equals("2")||data.equals("3")||data.equals("4")) {
//           replyMarkup.setReplyMarkup(keyboardService.getKeyboard(data));
//           messageEventPublisher.publish(replyMarkup);
//       } else if(data.contains("/")) {
//           data = data.replace("/", "");
//           userStorageService.add(new User(chatId, Country.valueOf(data)));
//           editMessageText.setText(String.format("Регион %s успешно установлен!Цены будут указаны в %S" +
//                   "\nВы всегда можете изменить его в меню бота",
//                   data.replace("/", "")
//                           + EmojiManager.getForAlias(data
//                           .toLowerCase(Locale.ROOT)).getUnicode(),
//                           Country.valueOf(data).getCurrency()
//                           + Country.valueOf(data).getSign()));
//           messageEventPublisher.publish(editMessageText);
//       } else {
//           editMessageText.setReplyMarkup(keyboardService.getKeyboard(data));
//           editMessageText.setText(String.format("Выбранный регион: %s. \nПодтвердите ваш выбор.", data
//                   + EmojiManager.getForAlias(data.toLowerCase(Locale.ROOT)).getUnicode()));
//           messageEventPublisher.publish(editMessageText);
//       }



    }

    public Command getCommand() {
        return Command.REGISTER;
    }
}
