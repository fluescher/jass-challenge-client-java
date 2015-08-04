package com.zuehlke.jasschallenge.client.websocket.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zuehlke.jasschallenge.client.websocket.RemoteGameHandler;
import com.zuehlke.jasschallenge.client.websocket.messages.responses.Response;
import com.zuehlke.jasschallenge.client.websocket.messages.type.RemoteCard;

import java.util.Optional;

public class RejectCard implements Message {
    private final RemoteCard data;

    public RejectCard(@JsonProperty(value = "data",required = true) RemoteCard remoteCard) {
        this.data = remoteCard;
    }

    @Override
    public Optional<Response> dispatch(RemoteGameHandler handler) {
        handler.onRejectCard(data);
        return Optional.empty();
    }

}