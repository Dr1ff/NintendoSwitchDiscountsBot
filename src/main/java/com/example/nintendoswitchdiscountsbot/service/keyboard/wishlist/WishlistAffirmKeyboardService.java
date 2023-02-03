package com.example.nintendoswitchdiscountsbot.service.keyboard.wishlist;

import com.example.nintendoswitchdiscountsbot.business.CallbackData;
import com.example.nintendoswitchdiscountsbot.business.Game;
import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.service.utils.KeyboardHelper;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

@Component
@RequiredArgsConstructor
public class WishlistAffirmKeyboardService implements WishlistKeyboardService {

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
        return Set.of(Subcommand.AFFIRM);
    }

    @Override
    public Set<Command> getCommands() {
        return Set.of(Command.WISHLIST);
    }

    @Override
    public InlineKeyboardMarkup getWishlistMarkup(CallbackData callbackData, List<Game> wishlist) {
        return getMarkup(callbackData);
    }

    private InlineKeyboardButton getCancelButton(CallbackData callbackData) {
        return keyboardHelper.getButton(callbackData.toBuilder()
                .subcommand(Optional.of(Subcommand.CANCEL))
                .build());
    }

    private InlineKeyboardButton getAcceptButton(CallbackData callbackData) {
        return keyboardHelper.getButton(callbackData.toBuilder()
                .subcommand(Optional.of(Subcommand.COMPLETE))
                .build());
    }
}
