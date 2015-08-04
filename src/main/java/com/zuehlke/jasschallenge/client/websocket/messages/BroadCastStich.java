package com.zuehlke.jasschallenge.client.websocket.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zuehlke.jasschallenge.client.websocket.RemoteGameHandler;
import com.zuehlke.jasschallenge.client.websocket.messages.responses.Response;
import com.zuehlke.jasschallenge.client.websocket.messages.type.Stich;

import java.util.Optional;

public class BroadCastStich implements Message {

    private final Stich stich;

    public BroadCastStich(
            @JsonProperty(value = "data",required = true) Stich stich) {
        this.stich = stich;
    }

    @Override
    public Optional<Response> dispatch(RemoteGameHandler handler) {
        handler.onBroadCastStich(stich);
        return Optional.empty();
    }
}
