package com.example.nintendoswitchdiscountsbot.service.command.processor.callback.subcommand;

import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.service.command.keyboard.KeyboardService;
import com.example.nintendoswitchdiscountsbot.service.command.processor.callback.CallbackData;
import com.example.nintendoswitchdiscountsbot.service.command.reply.RegisterReply;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class RegisterCallbackSubcommandProcessor implements CallbackSubcommandProcessor {

    private final Map<Subcommand, KeyboardService> keyboardServices;
    private final Map<Subcommand, RegisterReply> replyBuilders;

    public RegisterCallbackSubcommandProcessor(
            List<KeyboardService> keyboardServices,
            List<RegisterReply> replyBuilders
    ) {
        Map<Subcommand, KeyboardService> keyboardServiceMap = new HashMap<>();
        keyboardServices.forEach(keyboardService -> keyboardService.getSubcommand()
                .forEach(subcommand -> keyboardServiceMap.put(subcommand, keyboardService))
        );
        Map<Subcommand, RegisterReply> replyBuilderMap = new HashMap<>();
        replyBuilders.forEach(replyBuilder -> replyBuilder.getSubcommand()
                .forEach(subcommand -> replyBuilderMap.put(subcommand, replyBuilder)));
        this.keyboardServices = keyboardServiceMap;
        this.replyBuilders = replyBuilderMap;
    }

    @Override
    public void process(CallbackQuery callbackQuery, CallbackData callbackData) {
        replyBuilders.get(
                        callbackData.subcommand().orElseThrow(
                                () -> new IllegalArgumentException(
                                        "В RegisterCallbackSubcommandProcessor попала callbackData " +
                                                "с subcommand = Optional.empty"
                                )
                        )
                )
                .reply(
                        callbackQuery,
                        callbackData,
                        keyboardServices.get(callbackData.subcommand().get())
                                .getMarkup(callbackData)
                );
    }

    @Override
    public Set<Subcommand> getSubcommand() {
        return Set.of(
                Subcommand.PREV,
                Subcommand.NEXT,
                Subcommand.CONFIRM,
                Subcommand.CANCEL,
                Subcommand.ACCEPT
        );
    }
}
