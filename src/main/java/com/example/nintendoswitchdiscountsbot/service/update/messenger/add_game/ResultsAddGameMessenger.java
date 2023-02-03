package com.example.nintendoswitchdiscountsbot.service.update.messenger.add_game;

import com.example.nintendoswitchdiscountsbot.business.CallbackData;
import com.example.nintendoswitchdiscountsbot.business.Game;
import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.example.nintendoswitchdiscountsbot.service.observer.MessageEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class ResultsAddGameMessenger extends AddGameMessenger{

    private final static String PARSE_MODE = "Markdown";

    private final MessageEventPublisher messageEventPublisher;

    @Override
    public Set<Subcommand> getSubcommand() {
        return Set.of(Subcommand.AFFIRM);
    }

    @Override
    public void reply(CallbackQuery callbackQuery, CallbackData callbackData, InlineKeyboardMarkup replyMarkup) {
        replyWithResults(
                callbackQuery.getMessage().getChatId(),
                callbackQuery.getMessage().getMessageId(),
                replyMarkup
        );
    }

    public void replyWithoutResult(Long chatId, Integer messageId, InlineKeyboardMarkup replyMarkup) {
        messageEventPublisher.publish(SendMessage.builder()
                .chatId(chatId)
                .replyToMessageId(messageId)
                .replyMarkup(replyMarkup)
                .parseMode(PARSE_MODE)
                .text(String.format(
                        "По тексту в присланном вами сообщении не найдена конкретная игра. " +
                                "Проверьте присланное название на наличие орфографических ошибок, " +
                                "а так же на наличие игры в каталоге %s Если вы нашли неточность, " +
                                "пришлите корректное название снова.",
                        "[Nintendo eShop](https://www.nintendo.ru/Nintendo-eShop/Nintendo-eShop-1806894.html)."
                ))
                .build());
    }

    public void replyWithResults(
            Long chatId,
            Integer messageId,
            InlineKeyboardMarkup replyMarkup
    ) {
        messageEventPublisher.publish(SendMessage.builder()
                .chatId(chatId)
                .replyToMessageId(messageId)
                .replyMarkup(replyMarkup)
                .disableWebPagePreview(true)
                .parseMode(PARSE_MODE)
                .text( "Я прислал вам небольшой список самых подходящих вариантов, " +
                        "нажмите на кнопку с названием нужной игры. " +
                        "Если вашей игры нет в списке, поищите её точное название в "
                        + "[Nintendo eShop]" +
                        "(https://www.nintendo.ru/Nintendo-eShop/Nintendo-eShop-1806894.html)."
                )
                .build());
    }

    public void replyWithAffirmationRequest(
            Long chatId,
            Integer messageId,
            Game game,
            InlineKeyboardMarkup replyMarkup
    ) {

        messageEventPublisher.publish(EditMessageText.builder()
                .chatId(chatId)
                .messageId(messageId)
                .replyMarkup(replyMarkup)
                .parseMode(PARSE_MODE)
                .text(String.format(
                        """
                                Вы действительно хотите добавить
                                *%s*
                                в список для отселживания скидок?
                                """,
                        game.name()

                ))
                .build());
    }
}
