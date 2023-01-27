package com.example.nintendoswitchdiscountsbot.repository;

public class NotBeEmptyOptionalException extends IllegalArgumentException {
    public NotBeEmptyOptionalException(String message) {
        super(message);
    }
}
