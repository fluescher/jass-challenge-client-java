package com.zuehlke.jasschallenge.client.websocket.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zuehlke.jasschallenge.client.websocket.GameHandler;
import com.zuehlke.jasschallenge.client.websocket.messages.responses.Response;
import com.zuehlke.jasschallenge.client.websocket.messages.type.TrumpfChoice;

import java.util.Optional;

public class BroadCastTrumpf implements Message {
    private final TrumpfChoice data;

    public BroadCastTrumpf(@JsonProperty(value = "data", required = true) TrumpfChoice trumpfChoice) {
        this.data = trumpfChoice;
    }

    @Override
    public Optional<Response> dispatch(GameHandler handler) {
        handler.onBroadCastTrumpf(data);
        return Optional.empty();
    }
}
