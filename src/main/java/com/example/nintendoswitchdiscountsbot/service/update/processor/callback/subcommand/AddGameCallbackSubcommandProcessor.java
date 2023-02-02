package com.example.nintendoswitchdiscountsbot.service.update.processor.callback.subcommand;

import com.example.nintendoswitchdiscountsbot.business.CallbackData;
import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.repository.RepositorySearchException;
import com.example.nintendoswitchdiscountsbot.service.storage.GameStorageService;
import com.example.nintendoswitchdiscountsbot.service.keyboard.game.GameKeyboardService;
import com.example.nintendoswitchdiscountsbot.service.update.processor.callback.subcommand.args.integer.IntegerSubcommandArgs;
import com.example.nintendoswitchdiscountsbot.service.update.reply.add_game.AddGameMessenger;
import com.example.nintendoswitchdiscountsbot.service.update.reply.add_game.ResultsAddGameMessenger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Component
public class AddGameCallbackSubcommandProcessor implements CallbackSubcommandProcessor {

    private final Map<Subcommand, AddGameMessenger> messengers;
    private final ResultsAddGameMessenger resultsMessenger;
    private final Map<Subcommand, GameKeyboardService> keyboardServices;
    private final GameStorageService gameStorageService;

    public AddGameCallbackSubcommandProcessor(
            List<AddGameMessenger> messengers,
            List<GameKeyboardService> keyboardServices,
            ResultsAddGameMessenger resultsMessenger,
            GameStorageService gameStorageService
    ) {
        this.keyboardServices = new HashMap<>();
        keyboardServices
                .stream()
                .filter(service -> service.getCommands().contains(getCommand()))
                .forEach(keyboardService -> keyboardService.getSubcommands()
                        .forEach(subcommand ->
                                this.keyboardServices.put(subcommand, keyboardService)));
        Map<Subcommand, AddGameMessenger> messengerMap = new HashMap<>();
        this.messengers = messengerMap;
        messengers.forEach(messenger -> messenger.getSubcommand()
                .forEach(subcommand -> messengerMap.put(subcommand, messenger)));
        this.gameStorageService = gameStorageService;
        this.resultsMessenger = resultsMessenger;
    }

    @Override
    public void process(CallbackQuery callbackQuery, CallbackData callbackData) {

        if (getSubcommand(callbackData).equals(Subcommand.AFFIRM)) {
            resultsMessenger.replyWithAffirmationRequest(
                    callbackQuery.getMessage().getChatId(),
                    callbackQuery.getMessage().getMessageId(),
                    gameStorageService
                            .findByHashcode(
                                    getSubcommandArgs(callbackData).integer())
                            .orElseThrow(() -> new RepositorySearchException(
                                    "В AddGameCallbackSubcommandProcessor попала callbackData " +
                                            "с SubcommandArgs = hashcode отсутствующем в репозитории"
                            )),
                    keyboardServices.get(getSubcommand(callbackData)).getMarkup(callbackData)

            );
        } else {
            messengers.get(getSubcommand(callbackData)).reply(
                    callbackQuery,
                    callbackData,
                    keyboardServices
                            .get(getSubcommand(callbackData))
                            .getMarkup(callbackData)
            );
        }
    }

    @Override
    public Set<Subcommand> getSubcommands() {
        return Set.of(
                Subcommand.G_ADD,
                Subcommand.CANCEL,
                Subcommand.COMPLETE,
                Subcommand.AFFIRM
        );
    }

    @Override
    public Command getCommand() {
        return Command.G_ADD;
    }

    private Subcommand getSubcommand(CallbackData callbackData) {
        return callbackData.subcommand().orElseThrow(
                () -> new RepositorySearchException(
                        "В AddGameCallbackSubcommandProcessor попала callbackData " +
                                "с subcommand = Optional.empty"
                )
        );
    }

    private IntegerSubcommandArgs getSubcommandArgs(CallbackData callbackData) {
        return ((IntegerSubcommandArgs) callbackData
                .subcommandArgs()
                .orElseThrow(
                        () -> new RepositorySearchException(
                                "В AddGameCallbackSubcommandProcessor попала callbackData " +
                                        "с subcommandArgs = Optional.empty"
                        )
                )
        );
    }
}
