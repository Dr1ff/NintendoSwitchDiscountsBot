package com.example.nintendoswitchdiscountsbot.service.command.handler;

import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.service.command.processor.callback.CallbackDto;
import com.example.nintendoswitchdiscountsbot.service.command.processor.callback.CallbackParser;
import com.example.nintendoswitchdiscountsbot.service.command.processor.callback.command.CallbackCommandProcessor;
import com.example.nintendoswitchdiscountsbot.service.command.processor.message.MessageCommandProcessor;
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

    private final Map<Command, MessageCommandProcessor> commandProcessors;
    private final Map<Command, CallbackCommandProcessor> callbackProcessors;
    private final ObjectMapper objectMapper;
    private final CallbackParser callbackParser;

    public UpdateHandler(
            List<MessageCommandProcessor> commandProcessors,
            List<CallbackCommandProcessor> callbackProcessors,
            ObjectMapper objectMapper,
            CallbackParser callbackParser) {
        this.commandProcessors = commandProcessors
                .stream()
                .collect(Collectors.toMap(MessageCommandProcessor::getCommand, Function.identity()));
        this.callbackProcessors = callbackProcessors
                .stream()
                .collect(Collectors.toMap(CallbackCommandProcessor::getCommand, Function.identity()));
        this.objectMapper = objectMapper;
        this.callbackParser = callbackParser;
    }


    @SneakyThrows
    public void processing(Update update) {
       if (update.hasMessage() && update.getMessage().hasText()) {
           String messageText = update.getMessage().getText().trim().replace("/", "").toUpperCase();
           Optional<Command> commandO = Optional.of(Command.valueOf(messageText));
           commandO.ifPresent(command -> commandProcessors.get(command).process(update.getMessage()));
       } else if(update.hasCallbackQuery()) {
           var callbackData = callbackParser.fromDto(
                   objectMapper.readValue(update.getCallbackQuery().getData(), CallbackDto.class));

           callbackProcessors.get(
                   callbackData.command()).process(update.getCallbackQuery(), callbackData);
       }
    }
}
