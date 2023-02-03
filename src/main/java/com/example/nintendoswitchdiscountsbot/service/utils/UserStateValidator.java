package com.example.nintendoswitchdiscountsbot.service.utils;

import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.repository.RepositorySearchException;
import com.example.nintendoswitchdiscountsbot.service.storage.UserStorageService;
import com.example.nintendoswitchdiscountsbot.service.update.processor.callback.command.CallbackCommandProcessor;
import com.example.nintendoswitchdiscountsbot.service.update.processor.message.MessageCommandProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserStateValidator {

    private final UserStorageService userStorageService;

    public void validation(Long chatId, CallbackCommandProcessor callbackCommandProcessor) {
        if (currentUserStateIsIncorrect(chatId, callbackCommandProcessor.getCommand())) {
            setUserState(chatId, callbackCommandProcessor.getCommand());
        }
    }

    public void validation(Long chatId, MessageCommandProcessor messageCommandProcessor) {
        if (currentUserStateIsIncorrect(chatId, messageCommandProcessor.getCommand())) {
            setUserState(chatId, messageCommandProcessor.getCommand());
        }
    }

    private void setUserState(Long chatId, Command command) {
        userStorageService.add(
                userStorageService
                        .findById(chatId)
                        .orElseThrow(
                                () -> new RepositorySearchException(
                                        "В UserRepository не найден user с таким id")
                        )
                        .toBuilder()
                        .state(command)
                        .build()
        );
    }

    private boolean currentUserStateIsIncorrect(Long chatId, Command command) {
        return !userStorageService
                .findById(chatId)
                .orElseThrow(
                        () -> new RepositorySearchException(
                                "В UserRepository не найден user с таким id")
                )
                .state()
                .equals(command);
    }
}
