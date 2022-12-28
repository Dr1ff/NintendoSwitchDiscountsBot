package com.example.nintendoswitchdiscountsbot.service.command.processor.callback;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CallbackDataMapper {

    private final CallbackParser callbackParser;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    public String getJson(CallbackData data) {
        return objectMapper.writeValueAsString(
                callbackParser.fromData(data)
        );
    }

    @SneakyThrows
    public CallbackData getData(String json) {
        return callbackParser.fromDto(
                objectMapper.readValue(json, CallbackDto.class));
    }
}
