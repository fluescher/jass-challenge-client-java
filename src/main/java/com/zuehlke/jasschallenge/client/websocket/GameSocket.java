package com.zuehlke.jasschallenge.client.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zuehlke.jasschallenge.client.websocket.messages.Message;
import com.zuehlke.jasschallenge.client.websocket.messages.responses.Response;
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
        Optional<Response> response = msg.dispatch(handler);
        response.ifPresent(responseChannel::respond);
    }

    public void onClose(int statusCode, String reason) {
        logger.trace("Connection closed: {} - {}", statusCode, reason);
    }

    public void onConnect(ResponseChannel responseChannel) {
        this.responseChannel = responseChannel;
    }
}
