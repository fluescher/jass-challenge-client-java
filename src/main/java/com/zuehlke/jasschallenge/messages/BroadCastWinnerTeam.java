package com.zuehlke.jasschallenge.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zuehlke.jasschallenge.client.websocket.GameHandler;
import com.zuehlke.jasschallenge.messages.responses.Response;
import com.zuehlke.jasschallenge.messages.type.RemoteTeam;

import java.util.Optional;

public class BroadCastWinnerTeam implements Message {
    private final RemoteTeam data;

    public BroadCastWinnerTeam(@JsonProperty(value = "data",required = true)RemoteTeam remoteTeam) {
        this.data = remoteTeam;
    }

    @Override
    public Optional<Response> dispatch(GameHandler handler) {
        handler.onBroadCastWinnerTeam(data);
        return Optional.empty();
    }
}
