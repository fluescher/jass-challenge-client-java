package com.zuehlke.jasschallenge.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zuehlke.jasschallenge.client.websocket.GameHandler;
import com.zuehlke.jasschallenge.messages.responses.Response;
import com.zuehlke.jasschallenge.messages.type.Stich;

import java.util.Optional;

public class BroadCastStich implements Message {

    private final Stich stich;

    public BroadCastStich(
            @JsonProperty(value = "data",required = true) Stich stich) {
        this.stich = stich;
    }

    @Override
    public Optional<Response> dispatch(GameHandler handler) {
        handler.onBroadCastStich(stich);
        return Optional.empty();
    }
}
