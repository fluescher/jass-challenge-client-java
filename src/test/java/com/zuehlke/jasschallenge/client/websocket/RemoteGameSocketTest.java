package com.zuehlke.jasschallenge.client.websocket;

import com.zuehlke.jasschallenge.client.game.Player;
import com.zuehlke.jasschallenge.client.websocket.messages.*;
import com.zuehlke.jasschallenge.client.websocket.messages.type.RemoteCard;
import com.zuehlke.jasschallenge.client.websocket.messages.type.Stich;
import com.zuehlke.jasschallenge.client.websocket.messages.type.Team;
import org.eclipse.jetty.websocket.api.Session;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static com.zuehlke.jasschallenge.client.websocket.messages.type.RemoteColor.*;
import static com.zuehlke.jasschallenge.client.websocket.messages.type.SessionType.AUTOJOIN;
import static com.zuehlke.jasschallenge.client.websocket.messages.type.Trumpf.OBEABE;
import static java.util.Arrays.asList;
import static org.mockito.Mockito.*;

public class RemoteGameSocketTest {

    @Test
    public void onMessage_repliesWithChoosePlayerName_onRequestPlayerName() throws IOException {

        final Session session = mock(Session.class, RETURNS_DEEP_STUBS);
        final RemoteGameHandler handler = mock(RemoteGameHandler.class);
        when(handler.onRequestPlayerName()).thenReturn(new ChoosePlayerName("name"));
        RemoteGameSocket remoteGameSocket = new RemoteGameSocket(handler);
        remoteGameSocket.onConnect(session);

        remoteGameSocket.onMessage("{\"type\":\"REQUEST_PLAYER_NAME\"}");

        verify(session.getRemote()).sendString("{\"type\":\"CHOOSE_PLAYER_NAME\",\"data\":\"name\"}");
    }

    @Test
    public void onMessage_notifiesTheHandler_onDealCards() throws IOException {

        final Session session = mock(Session.class, RETURNS_DEEP_STUBS);

        final RemoteGameHandler handler = mock(RemoteGameHandler.class);
        RemoteGameSocket remoteGameSocket = new RemoteGameSocket(handler);
        remoteGameSocket.onConnect(session);

        remoteGameSocket.onMessage("{\"type\":\"DEAL_CARDS\",\"data\":[{\"number\":14,\"color\":\"DIAMONDS\"}," +
                "{\"number\":8,\"color\":\"SPADES\"}," +
                "{\"number\":6,\"color\":\"CLUBS\"}]}");

        verify(handler).onDealCards(eq(new DealCard(asList(new RemoteCard(14, DIAMONDS), new RemoteCard(8, SPADES), new RemoteCard(6, CLUBS)))));
    }

    @Test
    public void onMessage_repliesWitchSessionType_onChooseSession() throws IOException {

        final Session session = mock(Session.class, RETURNS_DEEP_STUBS);
        final RemoteGameHandler handler = mock(RemoteGameHandler.class);
        when(handler.onRequestSessionChoice()).thenReturn(new ChooseSession(AUTOJOIN));
        RemoteGameSocket remoteGameSocket = new RemoteGameSocket(handler);
        remoteGameSocket.onConnect(session);

        remoteGameSocket.onMessage("{\"type\":\"REQUEST_SESSION_CHOICE\"}");

        verify(session.getRemote()).sendString("{\"type\":\"CHOOSE_SESSION\"," +
                "\"data\":{\"sessionChoice\":\"AUTOJOIN\",\"sessionName\":\"a session\"}}");
    }

    @Test
    public void onMessage_sendsTrupf() throws IOException {

        final Session session = mock(Session.class, RETURNS_DEEP_STUBS);
        final RemoteGameHandler handler = mock(RemoteGameHandler.class);
        when(handler.onRequestTrumpf()).thenReturn(new ChooseTrumpf(OBEABE));
        RemoteGameSocket remoteGameSocket = new RemoteGameSocket(handler);
        remoteGameSocket.onConnect(session);

        remoteGameSocket.onMessage("{\"type\":\"REQUEST_TRUMPF\"}");

        verify(session.getRemote()).sendString("{\"type\":\"CHOOSE_TRUMPF\",\"data\":{\"mode\":\"OBEABE\",\"trumpfColor\":null}}");
    }

    @Test
    public void onMessage_callsOnPlayedCards_onPlayedCardsMessage() throws IOException {

        final Session session = mock(Session.class, RETURNS_DEEP_STUBS);
        final RemoteGameHandler handler = mock(RemoteGameHandler.class);
        RemoteGameSocket remoteGameSocket = new RemoteGameSocket(handler);
        remoteGameSocket.onConnect(session);

        remoteGameSocket.onMessage("{\"type\":\"PLAYED_CARDS\",\"data\":[{\"number\":13,\"color\":\"CLUBS\"}," +
                "{\"number\":10,\"color\":\"DIAMONDS\"}]}");

        verify(handler).onPlayedCards(eq(new PlayedCards(asList(new RemoteCard(13, CLUBS), new RemoteCard(10, DIAMONDS)))));
    }

    @Test
    public void onMessage_sendsCardToPlay_onRequestCard() throws IOException {

        final Session session = mock(Session.class, RETURNS_DEEP_STUBS);
        final RemoteGameHandler handler = mock(RemoteGameHandler.class);
        when(handler.onRequestCard()).thenReturn(new ChooseCard(new RemoteCard(14, DIAMONDS)));
        RemoteGameSocket remoteGameSocket = new RemoteGameSocket(handler);
        remoteGameSocket.onConnect(session);

        remoteGameSocket.onMessage("{\"type\":\"REQUEST_CARD\"}");

        verify(session.getRemote()).sendString("{\"type\":\"CHOOSE_CARD\",\"data\":{\"number\":14,\"color\":\"DIAMONDS\"}}");
    }

    @Test
    public void onMessage_canHandleBroadCastStich() throws IOException {

        final Session session = mock(Session.class, RETURNS_DEEP_STUBS);
        final RemoteGameHandler handler = mock(RemoteGameHandler.class);
        RemoteGameSocket remoteGameSocket = new RemoteGameSocket(handler);
        remoteGameSocket.onConnect(session);

        remoteGameSocket.onMessage("{\"type\":\"BROADCAST_STICH\",\"data\":{\"name\":\"1437909005411\",\"id\":2,\"playedCards\":[{\"number\":13,\"color\":\"DIAMONDS\"},{\"number\":13,\"color\":\"CLUBS\"},{\"number\":12,\"color\":\"CLUBS\"},{\"number\":12,\"color\":\"SPADES\"}],\"teams\":[{\"name\":\"Team 1\",\"points\":2148,\"currentRoundPoints\":348},{\"name\":\"Team 2\",\"points\":2547,\"currentRoundPoints\":108}]}}");

        final List<RemoteCard> playedCards = asList(new RemoteCard(13, DIAMONDS), new RemoteCard(13, CLUBS), new RemoteCard(12, CLUBS), new RemoteCard(12, SPADES));
        final List<Team> teams = asList(new Team("Team 1", 2148, 348), new Team("Team 2", 2547, 108));
        final Stich stich = new Stich("1437909005411", 2, playedCards, teams);
        final BroadCastStich message = new BroadCastStich(stich);
        verify(handler).onBroadCastStich(message);
    }

}