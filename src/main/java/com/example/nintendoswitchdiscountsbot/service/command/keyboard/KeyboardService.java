package com.example.nintendoswitchdiscountsbot.service.command.keyboard;

import com.example.nintendoswitchdiscountsbot.enums.Country;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class KeyboardService {
    private final InlineKeyboardMarkup inlineKeyboard;
    private List<InlineKeyboardButton> countryButtons;
    private final InlineKeyboardButton nextBtn;
    private final InlineKeyboardButton prevBtn;
    private final List<InlineKeyboardButton> firstRow;
    private final List<InlineKeyboardButton> secondRow;
    private final List<InlineKeyboardButton> thirdRow;


    public KeyboardService() {
        this.inlineKeyboard = new InlineKeyboardMarkup();
        this.nextBtn = new InlineKeyboardButton();
        this.prevBtn = new InlineKeyboardButton();
        this.firstRow = new ArrayList<>();
        this.secondRow = new ArrayList<>();
        this.thirdRow = new ArrayList<>();
    }

    public InlineKeyboardMarkup getKeyboard(String data) {

        switch (data) {
            case "cancel", "1" -> keyboard1();
            case "2" -> keyboard2();
            case "3" -> keyboard3();
            case "4" -> keyboard4();
            default -> confirmKeyboard(data);
        }
        return inlineKeyboard;
    }



    private void keyboard1() {
        firstRow.clear();
        secondRow.clear();
        thirdRow.clear();

        nextBtn.setText(EmojiParser.parseToUnicode(":arrow_right:"));
        nextBtn.setCallbackData("2");

        firstRow.addAll(countryButtons.subList(0, 4));
        secondRow.addAll(countryButtons.subList(4, 8));
        thirdRow.addAll(countryButtons.subList(8, 11));
        thirdRow.add(nextBtn);

        inlineKeyboard.setKeyboard(List.of(firstRow, secondRow, thirdRow));
    }

    private void keyboard2() {
        firstRow.clear();
        secondRow.clear();
        thirdRow.clear();

        nextBtn.setText(EmojiParser.parseToUnicode(":arrow_right:"));
        nextBtn.setCallbackData("3");
        prevBtn.setText(EmojiParser.parseToUnicode(":arrow_left:"));
        prevBtn.setCallbackData("1");

        firstRow.add(prevBtn);
        firstRow.addAll(countryButtons.subList(11, 14));
        secondRow.addAll(countryButtons.subList(14, 18));
        thirdRow.addAll(countryButtons.subList(18, 21));
        thirdRow.add(nextBtn);

        inlineKeyboard.setKeyboard(List.of(firstRow, secondRow, thirdRow));

    }

    private void keyboard3() {
        firstRow.clear();
        secondRow.clear();
        thirdRow.clear();

        nextBtn.setText(EmojiParser.parseToUnicode(":arrow_right:"));
        nextBtn.setCallbackData("4");
        prevBtn.setText(EmojiParser.parseToUnicode(":arrow_left:"));
        prevBtn.setCallbackData("2");

        firstRow.add(prevBtn);
        firstRow.addAll(countryButtons.subList(21, 24));
        secondRow.addAll(countryButtons.subList(24, 28));
        thirdRow.addAll(countryButtons.subList(28, 31));
        thirdRow.add(nextBtn);

        inlineKeyboard.setKeyboard(List.of(firstRow, secondRow, thirdRow));
    }

    private void keyboard4() {
        firstRow.clear();
        secondRow.clear();
        thirdRow.clear();

        prevBtn.setText(EmojiParser.parseToUnicode(":arrow_left:"));
        prevBtn.setCallbackData("3");

        firstRow.add(prevBtn);
        firstRow.addAll(countryButtons.subList(31, 34));
        secondRow.addAll(countryButtons.subList(34, 38));

        inlineKeyboard.setKeyboard(List.of(firstRow, secondRow));
    }

    private void confirmKeyboard(String country) {
        firstRow.clear();
        secondRow.clear();
        thirdRow.clear();

        prevBtn.setText(EmojiParser.parseToUnicode(":x:"));
        prevBtn.setCallbackData("cancel");
        nextBtn.setText(EmojiParser.parseToUnicode(":white_check_mark:"));
        nextBtn.setCallbackData("/" + country);
        firstRow.addAll(List.of(prevBtn, nextBtn));

        inlineKeyboard.setKeyboard(List.of(firstRow));
    }


    @PostConstruct
    private void setCountryButtons() {
        this.countryButtons = Arrays
                .stream(Country.values())
                .map(country -> {
                    InlineKeyboardButton button = new InlineKeyboardButton();
                    button.setText(country.name());
                    button.setCallbackData(country.name());
                    return button;
                }).toList();
    }

}
