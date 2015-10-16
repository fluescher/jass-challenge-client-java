package com.zuehlke.jasschallenge.client.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zuehlke.jasschallenge.client.websocket.messages.Message;
import com.zuehlke.jasschallenge.client.websocket.messages.responses.Response;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@WebSocket
public class RemoteGameSocket {

    private final static Logger logger = LoggerFactory.getLogger(RemoteGameSocket.class);
    private final CountDownLatch closeLatch = new CountDownLatch(1);
    private final GameHandler handler;
    private Session session;

    public RemoteGameSocket(GameHandler handler) {
        this.handler = handler;
    }

    public boolean awaitClose(int duration, TimeUnit unit) throws InterruptedException {
        return this.closeLatch.await(duration, unit);
    }

    @OnWebSocketConnect
    public void onConnect(Session session) {
        logger.trace("Got connect: {}", session);
        this.session = session;
    }

    @OnWebSocketMessage
    public void onMessage(String msg) throws IOException {
        logger.trace("Received message: {}", msg);

        final Message message = read(msg, Message.class);
        Optional<Response> response = message.dispatch(handler);
        response.ifPresent(this::send);
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        logger.trace("Connection closed: {} - {}", statusCode, reason);
        closeLatch.countDown();
    }

    private void send(Response message) {
        try {
            final String messageString = toJson(message);
            logger.trace("Sending message: {}", messageString);
            session.getRemote().sendString(messageString);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> T read(String msg, Class<T> valueType) throws IOException {
        return new ObjectMapper().readValue(msg, valueType);
    }

    private static String toJson(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
