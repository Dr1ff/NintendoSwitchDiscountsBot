package com.example.nintendoswitchdiscountsbot.service.update.reply.wishlist;

import com.example.nintendoswitchdiscountsbot.business.Game;
import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.service.observer.MessageEventPublisher;
import com.example.nintendoswitchdiscountsbot.service.update.reply.Messenger;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Component
@RequiredArgsConstructor
public class WishlistMessenger implements Messenger {

    private final static String PARSE_MODE = "html";

    private final MessageEventPublisher messageEventPublisher;

    public void selectReply(
            Long chatId,
            Integer messageId,
            InlineKeyboardMarkup replyMarkup
    ) {
        reply(
                getSelectText(),
                chatId,
                messageId,
                replyMarkup
        );
    }

    public void gameShowReply(
            Game game,
            Long chatId,
            Integer messageId,
            InlineKeyboardMarkup replyMarkup
    ) {
        reply(
                getGameShowText(game),
                chatId,
                messageId,
                replyMarkup
        );
    }

    public void gameRemoveReply(
            Game game,
            Long chatId,
            Integer messageId,
            InlineKeyboardMarkup replyMarkup
    ) {
        reply(
                getGameRemoveText(game),
                chatId,
                messageId,
                replyMarkup
        );
    }

    @Override
    public Set<Subcommand> getSubcommand() {
        return Set.of();
    }

    @Override
    public Command getCommand() {
        return Command.WISHLIST;
    }

    private void reply(
            String text,
            Long chatId,
            Integer messageId,
            InlineKeyboardMarkup replyMarkup
    ) {
        messageEventPublisher.publish(
                EditMessageText.builder()
                        .text(text)
                        .parseMode(PARSE_MODE)
                        .chatId(chatId)
                        .messageId(messageId)
                        .replyMarkup(replyMarkup)
                        .build()
        );
    }

    private String getSelectText() {
        return "Чтобы увидеть информацию об игре " +
                "или удалить ее, нажмите на кнопку с названием игры";
    }

    private String getGameShowText(Game game) {
        StringBuilder priceInfo = new StringBuilder();
        if (game.isDiscount()) {
            priceInfo
                    .append("<s>")
                    .append(game.priceWithoutDiscount().orElseThrow())
                    .append("</s>")
                    .append(game.country().getCurrency())
                    .append(" ")
                    .append("-")
                    .append(game.discountPercent().orElseThrow().intValue())
                    .append("%")
                    .append(" ")
                    .append(game.actualPrice())

                    .append(game.country().getCurrency());
        } else {
            priceInfo
                    .append(game.actualPrice())
                    .append(game.country().getCurrency());
        }
        return String.format(
                """
                        Информация об игре:
                        Название - <b>%s</b>
                        Актуальная цена в регионе <b>%s</b>: <b>%s</b>""",
                game.name(),
                game.country(),
                priceInfo
        );
    }

    private String getGameRemoveText(Game game) {
        return String.format("Игра %s успешно удалена из вашего списка", game.name());
    }
}
