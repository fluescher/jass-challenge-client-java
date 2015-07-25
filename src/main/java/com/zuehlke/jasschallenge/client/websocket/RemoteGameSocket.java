package com.zuehlke.jasschallenge.client.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zuehlke.jasschallenge.client.game.Player;
import com.zuehlke.jasschallenge.client.game.cards.Card;
import com.zuehlke.jasschallenge.client.websocket.messages.*;
import com.zuehlke.jasschallenge.client.websocket.messages.type.RemoteCard;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.zuehlke.jasschallenge.client.websocket.messages.ChooseSession.SessionType.AUTOJOIN;
import static java.util.stream.Collectors.toSet;

@WebSocket
public class RemoteGameSocket {

    private final static Logger logger = LoggerFactory.getLogger(RemoteGameSocket.class);
    private final CountDownLatch closeLatch = new CountDownLatch(1);
    private final Player localPlayer;
    private Session session;

    public RemoteGameSocket(Player player) {
        this.localPlayer = player;
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
        switch(message.getType()) {
            case REQUEST_PLAYER_NAME:
                send(new ChoosePlayerName(localPlayer.getName()));
                break;
            case REQUEST_SESSION_CHOICE:
                send(new ChooseSession(AUTOJOIN));
                break;
            case DEAL_CARDS:
                final DealCard dealCard = read(msg, DealCard.class);
                final Set<Card> cards = dealCard.getData().stream().map(RemoteGameSocket::mapCard).collect(toSet());
                System.out.println("cards = " + cards);
                localPlayer.setCards(cards);
                break;
            case REQUEST_TRUMPF:
                send(new ChooseTrumpf(ChooseTrumpf.Trumpf.OBEABE));
                break;
        }
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        logger.debug("Connection closed: {} - {}", statusCode, reason);
        closeLatch.countDown();
    }

    private static Card mapCard(RemoteCard remoteCard) {
        return Arrays.stream(Card.values())
                .filter(card -> card.getColor() == remoteCard.getColor().getMappedColor())
                .filter(card -> card.getRank() == remoteCard.getNumber() - 5)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Unable to map card"));
    }

    private void send(Object message) throws IOException {
        final String messageString = toJson(message);
        logger.debug("Sending message: {}", messageString);
        session.getRemote().sendString(messageString);
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
