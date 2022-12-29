package com.example.nintendoswitchdiscountsbot.service.update.handler;

import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.service.update.processor.callback.data.CallbackDataMapper;
import com.example.nintendoswitchdiscountsbot.service.update.processor.callback.data.CallbackParser;
import com.example.nintendoswitchdiscountsbot.service.update.processor.callback.command.CallbackCommandProcessor;
import com.example.nintendoswitchdiscountsbot.service.update.processor.message.MessageCommandProcessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class UpdateHandler {

    private final Map<Command, CallbackCommandProcessor> callbackProcessors;
    private final Map<Command, MessageCommandProcessor> commandProcessors;
    private final CallbackDataMapper callbackDataMapper;

    public UpdateHandler(
            List<MessageCommandProcessor> commandProcessors,
            List<CallbackCommandProcessor> callbackProcessors,
            ObjectMapper objectMapper,
            CallbackParser callbackParser, CallbackDataMapper callbackDataMapper) {
        this.callbackProcessors = callbackProcessors
                .stream()
                .collect(Collectors.toMap(CallbackCommandProcessor::getCommand, Function.identity()));
        this.commandProcessors = commandProcessors
                .stream()
                .collect(Collectors.toMap(MessageCommandProcessor::getCommand, Function.identity()));
        this.callbackDataMapper = callbackDataMapper;
    }

    @SneakyThrows
    public void handle(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText().trim().replace("/", "").toUpperCase();
            Optional<Command> commandO = Optional.of(Command.valueOf(messageText));
            commandO.ifPresent(command -> commandProcessors.get(command).process(update.getMessage()));
        } else if (update.hasCallbackQuery()) {
            var callbackData = callbackDataMapper.getData(update.getCallbackQuery().getData());
            callbackProcessors.get(callbackData.command())
                    .process(
                            update.getCallbackQuery(),
                            callbackData
                    );
        }
    }
}
