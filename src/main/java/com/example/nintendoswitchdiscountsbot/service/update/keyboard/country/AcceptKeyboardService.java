package com.example.nintendoswitchdiscountsbot.service.update.keyboard.country;

import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.business.CallbackData;
import com.example.nintendoswitchdiscountsbot.service.update.keyboard.KeyboardHelper;
import com.example.nintendoswitchdiscountsbot.service.update.keyboard.KeyboardService;
import com.vdurmont.emoji.EmojiManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class AcceptKeyboardService implements KeyboardService {

    private final KeyboardHelper keyboardHelper;

    @Override
    public InlineKeyboardMarkup getMarkup(CallbackData callbackData) {
        return InlineKeyboardMarkup.builder()
                .keyboardRow(
                        List.of(keyboardHelper.getButton(
                                        "Пуск" + EmojiManager.getForAlias("rocket").getUnicode(),
                                        CallbackData.builder()
                                                .command(Command.MENU)
                                                .commandArgs(Optional.empty())
                                                .subcommand(Optional.of(Subcommand.ACCEPT))
                                                .subcommandArgs(Optional.empty())
                                                .build()
                                )
                        )
                )
                .build();
    }

    @Override
    public Set<Subcommand> getSubcommands() {
        return Set.of(Subcommand.ACCEPT);
    }

    @Override
    public Set<Command> getCommands() {
        return Set.of(
                Command.REGISTER
        );
    }
}
