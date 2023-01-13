package com.example.nintendoswitchdiscountsbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NintendoSwitchDiscountsBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(NintendoSwitchDiscountsBotApplication.class, args);
    }

}
