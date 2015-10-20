package com.zuehlke.jasschallenge.client.websocket;

import com.zuehlke.jasschallenge.messages.Message;
import com.zuehlke.jasschallenge.messages.responses.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;

public class GameSocket {

    protected final GameHandler handler;
    protected ResponseChannel responseChannel;

    Logger logger = LoggerFactory.getLogger(getClass());

    public GameSocket(GameHandler handler) {
        this.handler = handler;
    }

    public void onMessage(Message msg) throws IOException {
        Optional<Response> response = dispatchMessage(msg);
        response.ifPresent(responseChannel::respond);
    }

    public Optional<Response> dispatchMessage(Message msg) {
        return msg.dispatch(handler);
    }

    public void onClose(int statusCode, String reason) {
        logger.trace("Connection closed: {} - {}", statusCode, reason);
    }

    public void onConnect(ResponseChannel responseChannel) {
        this.responseChannel = responseChannel;
    }
}
