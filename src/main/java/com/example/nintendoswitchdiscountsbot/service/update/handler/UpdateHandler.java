package com.example.nintendoswitchdiscountsbot.service.update.handler;

import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.service.storage.UserStorageService;
import com.example.nintendoswitchdiscountsbot.service.update.processor.callback.command.CallbackCommandProcessor;
import com.example.nintendoswitchdiscountsbot.service.update.processor.callback.data.CallbackDataMapper;
import com.example.nintendoswitchdiscountsbot.service.update.processor.message.MessageCommandProcessor;
import com.example.nintendoswitchdiscountsbot.service.update.processor.message.MessageNonCommandProcessor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class UpdateHandler {

    private final Map<Command, CallbackCommandProcessor> callbackProcessors;
    private final Map<Command, MessageCommandProcessor> commandProcessors;
    private final Map<Command, MessageNonCommandProcessor> nonCommandProcessors;
    private final Map<String, Optional<Command>> commandsO;
    private final CallbackDataMapper callbackDataMapper;
    private final UserStorageService userStorageService;

    public UpdateHandler(
            List<MessageCommandProcessor> commandProcessors,
            List<CallbackCommandProcessor> callbackProcessors,
            List<MessageNonCommandProcessor> nonCommandProcessors,
            CallbackDataMapper callbackDataMapper,
            UserStorageService userStorageService
    ) {
        this.callbackProcessors = callbackProcessors
                .stream()
                .collect(Collectors.toMap(CallbackCommandProcessor::getCommand, Function.identity()));
        this.commandProcessors = commandProcessors
                .stream()
                .collect(Collectors.toMap(MessageCommandProcessor::getCommand, Function.identity()));
        this.nonCommandProcessors = nonCommandProcessors
                .stream()
                .collect(Collectors.toMap(MessageNonCommandProcessor::getState, Function.identity()));
        this.commandsO = Arrays.stream(Command.values())
                .collect(Collectors.toMap(Command::name, Optional::of));
        this.callbackDataMapper = callbackDataMapper;
        this.userStorageService = userStorageService;
    }

    public void handle(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText().trim().replace("/", "").toUpperCase();
            var commandO = commandsO.getOrDefault(messageText, Optional.empty());
            var userO = userStorageService.findById(update.getMessage().getChatId());
            Command state;
            if (userO.isPresent()) {
                state = userO.get().state();
            } else {
                state = Command.REGISTER;
            }
            commandO.ifPresentOrElse(
                    command -> commandProcessors
                            .get(command)
                            .process(update.getMessage()),
                    () -> nonCommandProcessors
                            .get(state)
                            .process(update.getMessage())
            );

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
