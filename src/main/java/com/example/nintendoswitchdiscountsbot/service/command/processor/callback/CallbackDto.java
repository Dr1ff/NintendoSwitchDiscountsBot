package com.example.nintendoswitchdiscountsbot.service.command.processor.callback;

import com.example.nintendoswitchdiscountsbot.enums.Command;
import com.example.nintendoswitchdiscountsbot.enums.Subcommand;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

public record CallbackDto(
        @JsonProperty("c") Command command,
        @JsonProperty("s") Subcommand subcommand,
        @JsonProperty("ca") List<String> commandArgs,
        @JsonProperty("sa") List<String> subcommandArgs
) {
    @Builder(toBuilder = true) public CallbackDto {}
}
