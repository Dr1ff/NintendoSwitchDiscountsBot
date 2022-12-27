package com.example.nintendoswitchdiscountsbot.service.command.keyboard;

import com.example.nintendoswitchdiscountsbot.enums.Country;
import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.service.command.processor.callback.CallbackData;
import com.example.nintendoswitchdiscountsbot.service.command.processor.callback.CallbackParser;
import com.example.nintendoswitchdiscountsbot.service.command.processor.callback.subcommand.CountrySubcommandArgs;
import com.example.nintendoswitchdiscountsbot.service.command.processor.callback.subcommand.IntSubcommandArgs;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ConfirmKeyboardService implements KeyboardService {

    private final static int NUMBER_OF_BUTTONS = 9;

    private final ObjectMapper objectMapper;
    private final CallbackParser callbackParser;

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
                .callbackData(objectMapper.writeValueAsString(callbackParser.fromData(callbackData.toBuilder()
                                        .subcommand(Subcommand.CANCEL)
                                        .subcommandArgs(getCancelArgs(
                                                        ((CountrySubcommandArgs) callbackData.subcommandArgs())
                                                                .country()
                                                )
                                        )
                                        .build()
                                )
                        )
                )
                .build();
    }

    @SneakyThrows
    private InlineKeyboardButton getAcceptButton(CallbackData callbackData) {
        return InlineKeyboardButton.builder()
                .text(Subcommand.ACCEPT.getButtonText())
                .callbackData(objectMapper.writeValueAsString(callbackParser.fromData(callbackData.toBuilder()
                                        .subcommand(Subcommand.ACCEPT)
                                        .subcommandArgs(getAcceptArgs(
                                                        ((CountrySubcommandArgs) callbackData.subcommandArgs())
                                                                .country()
                                                )
                                        )
                                        .build()
                                )
                        )

                )
                .build();
    }

    private IntSubcommandArgs getCancelArgs(Country country) {
        return IntSubcommandArgs.builder()
                .firstButtonIndex(((country.ordinal()) / NUMBER_OF_BUTTONS) * NUMBER_OF_BUTTONS)
                .build();
    }

    private CountrySubcommandArgs getAcceptArgs(Country country) {
        return CountrySubcommandArgs.builder()
                .country(country)
                .build();
    }
}
