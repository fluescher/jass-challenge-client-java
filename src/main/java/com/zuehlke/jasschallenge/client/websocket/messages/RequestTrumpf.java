package com.zuehlke.jasschallenge.client.websocket.messages;

import com.zuehlke.jasschallenge.client.websocket.GameHandler;
import com.zuehlke.jasschallenge.client.websocket.messages.responses.Response;

import java.util.Optional;

public class RequestTrumpf implements Message {
    @Override
    public Optional<Response> dispatch(GameHandler handler) {
        return Optional.of(handler.onRequestTrumpf());
    }
}
