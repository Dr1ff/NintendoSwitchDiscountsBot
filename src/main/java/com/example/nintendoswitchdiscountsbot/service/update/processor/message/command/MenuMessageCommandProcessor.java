package com.example.nintendoswitchdiscountsbot.service.update.processor.message.command;

import com.example.nintendoswitchdiscountsbot.business.CallbackData;
import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.service.update.keyboard.menu.MenuKeyboardService;
import com.example.nintendoswitchdiscountsbot.service.update.processor.message.MessageCommandProcessor;
import com.example.nintendoswitchdiscountsbot.service.update.reply.menu.MenuMessenger;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class MenuMessageCommandProcessor implements MessageCommandProcessor {

    private final MenuKeyboardService keyboardService;
    private final Map<Subcommand, MenuMessenger> replies;

    public MenuMessageCommandProcessor(
            MenuKeyboardService keyboardService,
            List<MenuMessenger> replies
    ) {
        this.keyboardService = keyboardService;
        Map<Subcommand, MenuMessenger> replyMap = new HashMap<>();
        replies.forEach(reply -> reply.getSubcommand()
                .forEach(subcommand -> replyMap.put(subcommand, reply)));
        this.replies = replyMap;
    }

    @Override
    public void process(Message message) {
        replies.get(Subcommand.ACCEPT)
                .reply(
                        message.getChatId(),
                        keyboardService.getMarkup(new CallbackData(
                                Command.MENU,
                                Optional.of(Subcommand.ADD_GAME),
                                Optional.empty(),
                                Optional.empty()
                                )
                        )
                );
    }

    @Override
    public Command getCommand() {
        return Command.MENU;
    }
}
