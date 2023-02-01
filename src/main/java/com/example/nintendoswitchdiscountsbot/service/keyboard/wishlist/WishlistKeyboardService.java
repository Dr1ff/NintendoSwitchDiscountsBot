package com.example.nintendoswitchdiscountsbot.service.keyboard.wishlist;

import com.example.nintendoswitchdiscountsbot.business.CallbackData;
import com.example.nintendoswitchdiscountsbot.business.Game;
import com.example.nintendoswitchdiscountsbot.service.keyboard.KeyboardService;
import java.util.List;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public interface WishlistKeyboardService extends KeyboardService {

    InlineKeyboardMarkup getWishlistMarkup(CallbackData callbackData, List<Game> wishlist);
}
