package com.example.nintendoswitchdiscountsbot.service.utils;

import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.repository.RepositorySearchException;
import com.example.nintendoswitchdiscountsbot.service.storage.UserStorageService;
import com.example.nintendoswitchdiscountsbot.service.update.processor.callback.command.CallbackCommandProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserStateValidator {

    private final UserStorageService userStorageService;

    public void validation(Long chatId, CallbackCommandProcessor processor) {
        if (!currentUserStateIsCorrect(chatId, processor.getCommand())) {
            setUserState(chatId, processor.getCommand());
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

    private boolean currentUserStateIsCorrect(Long chatId, Command command) {
        return userStorageService
                .findById(chatId)
                .orElseThrow(
                        () -> new RepositorySearchException(
                                "В UserRepository не найден user с таким id")
                )
                .state()
                .equals(command);
    }
}
