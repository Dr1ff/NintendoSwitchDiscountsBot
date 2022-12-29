package com.example.nintendoswitchdiscountsbot.service.update.keyboard;

import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.business.CallbackData;
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
                                                .command(Command.BREAK)
                                                .commandArgs(Optional.empty())
                                                .subcommand(Optional.empty())
                                                .subcommandArgs(Optional.empty())
                                                .build()
                                )
                        )
                )
                .build();
    }

    @Override
    public Set<Subcommand> getSubcommand() {
        return Set.of(Subcommand.ACCEPT);
    }
}
