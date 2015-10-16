package com.zuehlke.jasschallenge.client.websocket.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zuehlke.jasschallenge.client.websocket.GameHandler;
import com.zuehlke.jasschallenge.client.websocket.messages.responses.Response;

import java.util.Optional;

public class PlayerJoined implements Message {


    private final PlayerJoinedSession session;

    public PlayerJoined(@JsonProperty(value = "data",required = true) PlayerJoinedSession session) {
        this.session = session;
    }

    @Override
    public Optional<Response> dispatch(GameHandler handler) {
        handler.onPlayerJoined(session);
        return Optional.empty();
    }

}
