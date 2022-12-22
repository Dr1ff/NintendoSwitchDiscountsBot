package com.example.nintendoswitchdiscountsbot.service.command.processor.callback;

import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CallbackCommandData {
    @JsonProperty("c")
    private Command command;
    @JsonProperty("s")
    private Subcommand subcommand;
    @JsonProperty("ca")
    private List<String> commandArgs;
    @JsonProperty("sa")
    private List<String> subcommandArgs;
}
