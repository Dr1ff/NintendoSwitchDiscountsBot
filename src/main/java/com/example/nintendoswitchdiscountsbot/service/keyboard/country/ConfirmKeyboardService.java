package com.example.nintendoswitchdiscountsbot.service.keyboard.country;

import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.enums.Country;
import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.business.CallbackData;
import com.example.nintendoswitchdiscountsbot.service.keyboard.KeyboardService;
import com.example.nintendoswitchdiscountsbot.service.utils.KeyboardHelper;
import com.example.nintendoswitchdiscountsbot.service.update.processor.callback.subcommand.args.country.CountrySubcommandArgs;
import com.example.nintendoswitchdiscountsbot.service.update.processor.callback.subcommand.args.integer.IntegerSubcommandArgs;
import com.example.nintendoswitchdiscountsbot.service.update.processor.callback.subcommand.args.SubcommandArgs;
import lombok.RequiredArgsConstructor;
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

    private final KeyboardHelper keyboardHelper;

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
    public Set<Subcommand> getSubcommands() {
        return Set.of(Subcommand.CONFIRM);
    }

    @Override
    public Set<Command> getCommands() {
        return Set.of(
                Command.REGISTER,
                Command.REGION
        );
    }

    private InlineKeyboardButton getCancelButton(CallbackData callbackData) {
        return keyboardHelper.getButton(callbackData.toBuilder()
                .subcommand(Optional.of(Subcommand.CANCEL))
                .subcommandArgs(getCancelArgs(
                        ((CountrySubcommandArgs) callbackData.subcommandArgs().orElseThrow(
                                () -> new IllegalArgumentException(
                                        "В ConfirmKeyboardService попала callbackData " +
                                                "с subcommandArgs = Optional.empty")
                        )).country()
                ))
                .build());
    }

    private InlineKeyboardButton getAcceptButton(CallbackData callbackData) {
        return keyboardHelper.getButton(callbackData.toBuilder()
                .subcommand(Optional.of(Subcommand.ACCEPT))
                .subcommandArgs(getAcceptArgs(
                        ((CountrySubcommandArgs) callbackData.subcommandArgs().orElseThrow(
                                () -> new IllegalArgumentException(
                                        "В ConfirmKeyboardService попала callbackData " +
                                                "с subcommandArgs = Optional.empty"
                                )
                        )).country()
                ))
                .build());
    }

    private Optional<SubcommandArgs> getCancelArgs(Country country) {
        return Optional.of(
                new IntegerSubcommandArgs(((country.ordinal()) / NUMBER_OF_BUTTONS) * NUMBER_OF_BUTTONS)
        );
    }

    private Optional<SubcommandArgs> getAcceptArgs(Country country) {
        return Optional.of(new CountrySubcommandArgs(country));
    }
}
