package com.example.nintendoswitchdiscountsbot.service.command.keyboard;

import com.example.nintendoswitchdiscountsbot.enums.Country;
import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.service.command.processor.callback.CallbackData;
import com.example.nintendoswitchdiscountsbot.service.command.processor.callback.CallbackDataMapper;
import com.example.nintendoswitchdiscountsbot.service.command.processor.callback.subcommand.CountrySubcommandArgs;
import com.example.nintendoswitchdiscountsbot.service.command.processor.callback.subcommand.IntSubcommandArgs;
import com.example.nintendoswitchdiscountsbot.service.command.processor.callback.subcommand.SubcommandArgs;
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
public class ConfirmKeyboardService implements KeyboardService {

    private final static int NUMBER_OF_BUTTONS = 9;

    private final CallbackDataMapper callbackDataMapper;

    @Override
    public InlineKeyboardMarkup getMarkup(CallbackData callbackData) {
        return InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(
                                getCancelButton(callbackData),
                                getAcceptButton(callbackData)
                        )
                )
                .build();
    }

    @Override
    public Set<Subcommand> getSubcommand() {
        return Set.of(Subcommand.CONFIRM);
    }

    @SneakyThrows
    private InlineKeyboardButton getCancelButton(CallbackData callbackData) {
        return InlineKeyboardButton.builder()
                .text(Subcommand.CANCEL.getButtonText())
                .callbackData(callbackDataMapper.getJson(
                        callbackData.toBuilder()
                                .subcommand(Optional.of(Subcommand.CANCEL))
                                .subcommandArgs(getCancelArgs(
                                        ((CountrySubcommandArgs) callbackData.subcommandArgs().orElseThrow(
                                                () -> new IllegalArgumentException(
                                                        "В ConfirmKeyboardService попала callbackData " +
                                                                "с subcommandArgs = Optional.empty")
                                        )).country()
                                ))
                                .build()
                ))
                .build();
    }

    @SneakyThrows
    private InlineKeyboardButton getAcceptButton(CallbackData callbackData) {
        return InlineKeyboardButton.builder()
                .text(Subcommand.ACCEPT.getButtonText())
                .callbackData(callbackDataMapper.getJson(
                        callbackData.toBuilder()
                                .subcommand(Optional.of(Subcommand.ACCEPT))
                                .subcommandArgs(getAcceptArgs(
                                        ((CountrySubcommandArgs) callbackData.subcommandArgs().orElseThrow(
                                                () -> new IllegalArgumentException(
                                                        "В ConfirmKeyboardService попала callbackData " +
                                                                "с subcommandArgs = Optional.empty"
                                                )
                                        )).country()
                                ))
                                .build()
                ))
                .build();
    }

    private Optional<SubcommandArgs> getCancelArgs(Country country) {
        return Optional.of(
                new IntSubcommandArgs(((country.ordinal()) / NUMBER_OF_BUTTONS) * NUMBER_OF_BUTTONS));
    }

    private Optional<SubcommandArgs> getAcceptArgs(Country country) {
        return Optional.of(new CountrySubcommandArgs(country));
    }
}
