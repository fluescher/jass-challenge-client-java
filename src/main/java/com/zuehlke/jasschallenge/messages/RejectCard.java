package com.zuehlke.jasschallenge.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zuehlke.jasschallenge.client.websocket.GameHandler;
import com.zuehlke.jasschallenge.messages.responses.Response;
import com.zuehlke.jasschallenge.messages.type.RemoteCard;

import java.util.Optional;

public class RejectCard implements Message {
    private final RemoteCard data;

    public RejectCard(@JsonProperty(value = "data",required = true) RemoteCard remoteCard) {
        this.data = remoteCard;
    }

    @Override
    public Optional<Response> dispatch(GameHandler handler) {
        handler.onRejectCard(data);
        return Optional.empty();
    }

}
