package com.example.nintendoswitchdiscountsbot.service.command.processor.callback.subcommand;

import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.service.command.processor.callback.CallbackCommandData;
import com.example.nintendoswitchdiscountsbot.service.command.reply.RegisterReplyBuilder;
import com.example.nintendoswitchdiscountsbot.service.command.keyboard.KeyboardService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class RegisterCallbackSubcommandProcessor implements CallbackSubcommandProcessor {
    private final Map<Subcommand, KeyboardService> keyboardServices;
    private final Map<Subcommand, RegisterReplyBuilder> replyBuilders;


    public RegisterCallbackSubcommandProcessor(
            List<KeyboardService> keyboardServices,
            List<RegisterReplyBuilder> replyBuilders
    ) {
        Map<Subcommand, KeyboardService> keyboardServiceMap = new HashMap<>();
        keyboardServices.forEach(keyboardService -> keyboardService.getSubcommand()
                .forEach(subcommand -> keyboardServiceMap.put(subcommand, keyboardService))
        );
        Map<Subcommand, RegisterReplyBuilder> replyBuilderMap = new HashMap<>();
        replyBuilders.forEach(replyBuilder -> replyBuilder.getSubcommand()
                .forEach(subcommand -> replyBuilderMap.put(subcommand, replyBuilder)));
        this.keyboardServices = keyboardServiceMap;
        this.replyBuilders = replyBuilderMap;
    }

    @Override
    public void process(CallbackQuery callbackQuery, CallbackCommandData commandData) {
        replyBuilders.get(
                commandData.getSubcommand()).build(callbackQuery, commandData,
                keyboardServices.get(commandData.getSubcommand()).getMarkup(commandData));
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
