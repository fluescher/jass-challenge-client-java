package com.zuehlke.jasschallenge.client.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zuehlke.jasschallenge.client.websocket.messages.*;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@WebSocket
public class RemoteGameSocket {

    private final static Logger logger = LoggerFactory.getLogger(RemoteGameSocket.class);
    private final CountDownLatch closeLatch = new CountDownLatch(1);
    private final RemoteGameHandler handler;
    private Session session;

    public RemoteGameSocket(RemoteGameHandler handler) {
        this.handler = handler;
    }

    public boolean awaitClose(int duration, TimeUnit unit) throws InterruptedException {
        return this.closeLatch.await(duration, unit);
    }

    @OnWebSocketConnect
    public void onConnect(Session session) {
        logger.debug("Got connect: {}", session);
        this.session = session;
    }

    @OnWebSocketMessage
    public void onMessage(String msg) throws IOException {
        logger.debug("Received message: {}", msg);

        final Message message = read(msg, Message.class);
        switch (message.getType()) {
            case REQUEST_PLAYER_NAME:
                send(handler.onRequestPlayerName());
                break;
            case REQUEST_SESSION_CHOICE:
                send(handler.onRequestSessionChoice());
                break;
            case DEAL_CARDS:
                handler.onDealCards(read(msg, DealCard.class));
                break;
            case REQUEST_TRUMPF:
                send(handler.onRequestTrumpf());
                break;
            case REQUEST_CARD:
                send(handler.onRequestCard());
                break;
            case PLAYED_CARDS:
                handler.onPlayedCards(read(msg, PlayedCards.class));
                break;
            case BROADCAST_STICH:
                handler.onBroadCastStich(read(msg, BroadCastStich.class));
                break;
            default:
                logger.warn("Recevice unkown message: {}", msg);
        }
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        logger.debug("Connection closed: {} - {}", statusCode, reason);
        closeLatch.countDown();
    }

    private void send(Message message) throws IOException {
        final String messageString = toJson(message);
        logger.debug("Sending message: {}", messageString);
        session.getRemote().sendString(messageString);
    }

    private static <T> T read(String msg, Class<T> valueType) throws IOException {
        return new ObjectMapper().readValue(msg, valueType);
    }

    private static String toJson(Message obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
