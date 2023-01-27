package com.example.nintendoswitchdiscountsbot.utils;

import com.example.nintendoswitchdiscountsbot.business.Game;

import java.util.Comparator;

public class GameComparator implements Comparator<Game> {

    private final String prefix;

    public GameComparator(String prefix) {
        this.prefix = prefix.toLowerCase();
    }

    @Override
    public int compare(Game o1, Game o2) {
        return Boolean.compare(
                o1.name().toLowerCase().startsWith(prefix),
                o2.name().toLowerCase().startsWith(prefix)
        );
    }
}
