package com.example.nintendoswitchdiscountsbot.service.command.keyboard;

import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.service.command.processor.callback.CallbackData;
import com.example.nintendoswitchdiscountsbot.service.command.processor.callback.CallbackDataMapper;
import com.vdurmont.emoji.EmojiManager;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class AcceptKeyboardService implements KeyboardService {

    private final CallbackDataMapper callbackDataMapper;

    @Override
    @SneakyThrows
    public InlineKeyboardMarkup getMarkup(CallbackData callbackData) {
        return InlineKeyboardMarkup.builder()
                .keyboardRow(
                        List.of(
                                InlineKeyboardButton.builder()
                                        .text("Пуск" + EmojiManager.getForAlias("rocket").getUnicode())
                                        .callbackData(
                                                callbackDataMapper.getJson(
                                                        CallbackData.builder()
                                                                .command(Command.BREAK)
                                                                .commandArgs(Optional.empty())
                                                                .subcommand(Optional.empty())
                                                                .subcommandArgs(Optional.empty())
                                                                .build()
                                                )
                                        )
                                        .build()
                        )
                )
                .build();
    }

    @Override
    public Set<Subcommand> getSubcommand() {
        return Set.of(Subcommand.ACCEPT);
    }
}
