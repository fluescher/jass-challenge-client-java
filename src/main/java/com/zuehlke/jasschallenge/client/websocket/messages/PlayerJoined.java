package com.zuehlke.jasschallenge.client.websocket.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zuehlke.jasschallenge.client.websocket.RemoteGameHandler;
import com.zuehlke.jasschallenge.client.websocket.messages.responses.Response;
import com.zuehlke.jasschallenge.client.websocket.messages.type.RemotePlayer;

import java.util.Optional;

public class PlayerJoined implements Message {

    private final RemotePlayer joinedPlayer;

    public PlayerJoined(@JsonProperty(value = "data",required = true) RemotePlayer remotePlayer) {
        joinedPlayer = remotePlayer;
    }

    @Override
    public Optional<Response> dispatch(RemoteGameHandler handler) {
        handler.onPlayerJoined(joinedPlayer);
        return Optional.empty();
    }

}
