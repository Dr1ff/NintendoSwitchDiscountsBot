package com.example.nintendoswitchdiscountsbot.service.update.processor.callback.command;

import com.example.nintendoswitchdiscountsbot.business.CallbackData;
import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.service.observer.MessageEventPublisher;
import com.vdurmont.emoji.EmojiParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Component
@RequiredArgsConstructor
public class BreakCallbackCommandProcessor implements CallbackCommandProcessor {

    private final MessageEventPublisher messageEventPublisher;

    @Override
    public void process(CallbackQuery callbackQuery, CallbackData callbackData) {
        callbackData.subcommand().ifPresent(subcommand -> {
                    if (subcommand.equals(Subcommand.CLEAN)) {
                        messageEventPublisher.publish(
                                DeleteMessage.builder()
                                        .chatId(callbackQuery.getFrom().getId())
                                        .messageId(callbackQuery.getMessage().getMessageId())
                                        .build()
                        );
                    }
                }
        );
        messageEventPublisher.publish(AnswerCallbackQuery.builder()
                .text(EmojiParser.parseToUnicode(":ghost:Призрачная кнопка!:scream:"))
                .callbackQueryId(callbackQuery.getId())
                .showAlert(false)
                .build());
    }

    @Override
    public Command getCommand() {
        return Command.BREAK;
    }
}
