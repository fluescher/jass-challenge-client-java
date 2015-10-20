package com.zuehlke.jasschallenge.client.websocket;

import org.eclipse.jetty.io.RuntimeIOException;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;

public class WebSocketConnection implements IConnection<String> {

    private final Session session;

    public WebSocketConnection(Session session) {
        this.session = session;
    }

    @Override
    public void send(String text) {
        try {
            session.getRemote().sendString(text);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
