package com.example.nintendoswitchdiscountsbot.service.update.processor.message.non_command;

import com.example.nintendoswitchdiscountsbot.business.CallbackData;
import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.service.storage.UserStorageService;
import com.example.nintendoswitchdiscountsbot.service.keyboard.game.GameKeyboardService;
import com.example.nintendoswitchdiscountsbot.service.update.processor.callback.subcommand.args.integer.IntegerSubcommandArgs;
import com.example.nintendoswitchdiscountsbot.service.update.processor.message.MessageNonCommandProcessor;
import com.example.nintendoswitchdiscountsbot.service.update.reply.add_game.ResultsAddGameMessenger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class MessageAddGameNonCommandProcessor implements MessageNonCommandProcessor {

    private final static int FIRST_INDEX = 0;
    private final static int MIN_RESULTS = 0;
    private final static int MAX_RESULTS = 5;

    private final UserStorageService userStorageService;
    private final Map<Subcommand, GameKeyboardService> keyboardServices;
    private final ResultsAddGameMessenger messenger;

    public MessageAddGameNonCommandProcessor(
            UserStorageService userStorageService,
            List<GameKeyboardService> keyboardServices,
            ResultsAddGameMessenger messenger
    ) {
        this.messenger = messenger;
        this.keyboardServices = new HashMap<>();
        keyboardServices
                .forEach(keyboardService -> keyboardService.getSubcommands()
                        .forEach(subcommand -> this.keyboardServices.put(subcommand, keyboardService)));
        this.userStorageService = userStorageService;
    }

    @Override
    public void process(Message message) {
        var games = userStorageService.getGamesOnRequestFromUser(
                message.getText(),
                message.getFrom().getId()
        );

        if (games.size() == MIN_RESULTS || games.size() > MAX_RESULTS) {
            messenger.replyWithoutResult(
                    message.getChatId(),
                    message.getMessageId(),
                    keyboardServices.get(Subcommand.BACK).getMarkup(
                            new CallbackData(
                                    Command.ADD_GAME,
                                    Optional.of(Subcommand.BACK),
                                    Optional.empty(),
                                    Optional.empty()
                            )
                    )
            );
        } else {
            messenger.replyWithResults(
                    message.getChatId(),
                    message.getMessageId(),
                    keyboardServices.get(Subcommand.SELECT).getGamesMarkup(
                            new CallbackData(
                                    Command.ADD_GAME,
                                    Optional.of(Subcommand.SELECT),
                                    Optional.empty(),
                                    Optional.of(new IntegerSubcommandArgs(FIRST_INDEX))
                            ),
                            games
                    )
            );
        }
    }

    @Override
    public Command getState() {
        return Command.ADD_GAME;
    }
}
