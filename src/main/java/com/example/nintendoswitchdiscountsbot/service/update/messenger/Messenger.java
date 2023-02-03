package com.example.nintendoswitchdiscountsbot.service.update.messenger;

import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.enums.Subcommand;

import java.util.Set;

public interface Messenger {

    Set<Subcommand> getSubcommand();

    Command getCommand();

}
