package com.example.nintendoswitchdiscountsbot.service.update.processor.message.non_command;

import com.example.nintendoswitchdiscountsbot.business.CallbackData;
import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.service.keyboard.clean.CleanKeyboardService;
import com.example.nintendoswitchdiscountsbot.service.observer.MessageEventPublisher;
import com.example.nintendoswitchdiscountsbot.service.update.processor.message.MessageNonCommandProcessor;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
public class MessageUnexpectedNonCommandProcessor implements MessageNonCommandProcessor {

    private final static Long DELAY = 3L;

    private final MessageEventPublisher messageEventPublisher;
    private final CleanKeyboardService closeKeyboardService;

    @Override
    public void process(Message message) {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        alertReply(message.getChatId(), message.getMessageId());
        executorService.schedule(
                () -> deleteUserMessage(message.getChatId(), message.getMessageId()),
                DELAY,
                TimeUnit.SECONDS
        );
    }

    @Override
    public Set<Command> getState() {
        return Set.of(
                Command.MENU,
                Command.START,
                Command.REGION,
                Command.REGISTER,
                Command.WISHLIST,
                Command.NOTIFICATION
        );
    }

    private void alertReply(Long chatId, Integer messageId) {
        messageEventPublisher.publish(
                SendMessage.builder()
                        .chatId(chatId)
                        .replyToMessageId(messageId)
                        .text(getText())
                        .replyMarkup(closeKeyboardService.getMarkup(
                                new CallbackData(
                                        Command.BREAK,
                                        Optional.of(Subcommand.CLEAN),
                                        Optional.empty(),
                                        Optional.empty()
                                )
                        ))
                        .build()
        );
    }

    private String getText() {
        return """
                ⛔На данном этапе я не ожидаю от вас сообщений!
                Присланное вами сообщение будет удалено в течении 3-х секунд!
                Для взаимодействия используйте кнопки, в присланом мной выше сообщении.
                Нажмите на кнопку чтобы удалить данное сообщение""";
    }

    private void deleteUserMessage(Long chatId, Integer messageId) {
        messageEventPublisher.publish(
                DeleteMessage.builder()
                        .chatId(chatId)
                        .messageId(messageId)
                        .build()
        );
    }

}
