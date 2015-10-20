package com.zuehlke.jasschallenge.messages;

import com.zuehlke.jasschallenge.client.websocket.GameHandler;
import com.zuehlke.jasschallenge.messages.responses.Response;

import java.util.Optional;

public class RequestSessionChoice implements Message {
    @Override
    public Optional<Response> dispatch(GameHandler handler) {
        return Optional.of(handler.onRequestSessionChoice());
    }
}
