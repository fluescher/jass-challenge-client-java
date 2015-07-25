package com.zuehlke.jasschallenge.client.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zuehlke.jasschallenge.client.game.Game;
import com.zuehlke.jasschallenge.client.game.Player;
import com.zuehlke.jasschallenge.client.game.Round;
import com.zuehlke.jasschallenge.client.game.cards.Card;
import com.zuehlke.jasschallenge.client.websocket.messages.*;
import com.zuehlke.jasschallenge.client.websocket.messages.type.RemoteCard;
import com.zuehlke.jasschallenge.client.websocket.messages.type.RemoteColor;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static com.zuehlke.jasschallenge.client.websocket.messages.ChooseSession.SessionType.AUTOJOIN;
import static java.util.stream.Collectors.toSet;

@WebSocket
public class RemoteGameSocket {

    private final static Logger logger = LoggerFactory.getLogger(RemoteGameSocket.class);
    private final CountDownLatch closeLatch = new CountDownLatch(1);
    private final Player localPlayer;
    private Session session;
    private Round currentRound = Round.createRound(0);

    public RemoteGameSocket(Game game, Player player) {
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
                localPlayer.setCards(mapAllToCards(dealCard.getData()));
                break;
            case REQUEST_TRUMPF:
                send(new ChooseTrumpf(ChooseTrumpf.Trumpf.OBEABE));
                break;
            case REQUEST_CARD:
                send(new ChooseCard(mapToRemoteCard(localPlayer.getNextCard(currentRound))));
                break;
            case PLAYED_CARDS:
                final PlayedCards playedCards = read(msg, PlayedCards.class);
                currentRound = createRound(playedCards.getData());
                break;
            default:
                logger.warn("Recevice unkown message: {}", msg);
        }
    }

    private static Round createRound(List<RemoteCard> remoteCards) {
        final Round r = Round.createRound(0);
        remoteCards.forEach(remoteCard -> r.playCard(null, mapToCard(remoteCard)));
        return r;
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        logger.debug("Connection closed: {} - {}", statusCode, reason);
        closeLatch.countDown();
    }

    private static Set<Card> mapAllToCards(List<RemoteCard> remoteCards) {
        return remoteCards.stream().map(RemoteGameSocket::mapToCard).collect(toSet());
    }

    private static RemoteCard mapToRemoteCard(Card card) {
        final RemoteColor remoteColor = Arrays.stream(RemoteColor.values())
                .filter(color -> color.getMappedColor() == card.getColor())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Could not map color"));
        return new RemoteCard(card.getRank()+5, remoteColor);
    }

    private static Card mapToCard(RemoteCard remoteCard) {
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
