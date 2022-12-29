package com.example.nintendoswitchdiscountsbot.enums;

import lombok.Getter;

/**
 * @author Alexander Popov
 */
@Getter
public enum Country {
    AT("EUR", "€"),
    AU("AUD", "$"),
    BE("EUR", "€"),
    BG("EUR", "€"),
    BR("BRL", "R$"),
    CA("CAD", "$"),
    CH("CHF", "CHF"),
    CY("EUR", "€"),
    CZ("CZK", "Kč"),
    DE("EUR", "€"),
    DK("DKK", "Kr"),
    EE("EUR", "€"),
    ES("EUR", "€"),
    FI("EUR", "€"),
    FR("EUR", "€"),
    GB("GBP", "£"),
    GR("EUR", "€"),
    HR("EUR", "€"),
    HU("EUR", "€"),
    IE("EUR", "€"),
    IT("EUR", "€"),
    LT("EUR", "€"),
    LU("EUR", "€"),
    LV("EUR", "€"),
    MT("EUR", "€"),
    MX("MXN", "$"),
    NL("EUR", "€"),
    NO("NOK", "kr"),
    NZ("NZD", "$"),
    PL("PLN", "zl"),
    PT("EUR", "€"),
    RO("EUR", "€"),
    RU("RUB", "₽"),
    SE("SEK", "Kr"),
    SI("EUR", "€"),
    SK("EUR", "€"),
    US("USD", "$"),
    ZA("ZAR", "R");

    private final String currency;
    private final String sign;
    Country(String currency, String sign) {
        this.currency = currency;
        this.sign = sign;
    }
}
