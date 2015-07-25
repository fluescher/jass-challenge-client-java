package com.zuehlke.jasschallenge.client.websocket;

import com.zuehlke.jasschallenge.client.game.Player;
import com.zuehlke.jasschallenge.client.game.cards.Card;
import org.eclipse.jetty.websocket.api.RemoteEndpoint;
import org.eclipse.jetty.websocket.api.Session;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Mockito.*;

public class RemoteGameSocketTest {

    @Test
    public void onMessage_repliesWithChoosePlayerName_onRequestPlayerName() throws IOException {

        final Session session = mock(Session.class);
        final RemoteEndpoint endpoint = mock(RemoteEndpoint.class);
        when(session.getRemote()).thenReturn(endpoint);

        RemoteGameSocket remoteGameSocket = new RemoteGameSocket(new Player("name"));
        remoteGameSocket.onConnect(session);

        remoteGameSocket.onMessage("{\"type\":\"REQUEST_PLAYER_NAME\"}");

        verify(endpoint).sendString("{\"type\":\"CHOOSE_PLAYER_NAME\",\"data\":\"name\"}");
    }

    @Test
    public void onMessage_repliesWitchSessionType_onChooseSession() throws IOException {

        final Session session = mock(Session.class);
        final RemoteEndpoint endpoint = mock(RemoteEndpoint.class);
        when(session.getRemote()).thenReturn(endpoint);

        final Player player = new Player("name");
        RemoteGameSocket remoteGameSocket = new RemoteGameSocket(player);
        remoteGameSocket.onConnect(session);

        remoteGameSocket.onMessage("{\"type\":\"DEAL_CARDS\",\"data\":[{\"number\":14,\"color\":\"DIAMONDS\"}," +
                "{\"number\":8,\"color\":\"SPADES\"}," +
                "{\"number\":6,\"color\":\"CLUBS\"}]}");

        assertThat(player.getCards(), containsInAnyOrder(Card.DIAMOND_ACE, Card.SPADE_EIGHT, Card.CLUB_SIX));
    }

    @Test
    public void onMessage_storesCardOnLocalPlayer() throws IOException {

        final Session session = mock(Session.class);
        final RemoteEndpoint endpoint = mock(RemoteEndpoint.class);
        when(session.getRemote()).thenReturn(endpoint);

        RemoteGameSocket remoteGameSocket = new RemoteGameSocket(new Player("name"));
        remoteGameSocket.onConnect(session);

        remoteGameSocket.onMessage("{\"type\":\"REQUEST_SESSION_CHOICE\"}");

        verify(endpoint).sendString("{\"type\":\"CHOOSE_SESSION\"," +
                "\"data\":{\"sessionChoice\":\"AUTOJOIN\",\"sessionName\":\"a session\"}}");
    }

    @Test
    public void onMessage_sendsTrupf() throws IOException {

        final Session session = mock(Session.class);
        final RemoteEndpoint endpoint = mock(RemoteEndpoint.class);
        when(session.getRemote()).thenReturn(endpoint);

        RemoteGameSocket remoteGameSocket = new RemoteGameSocket(new Player("name"));
        remoteGameSocket.onConnect(session);

        remoteGameSocket.onMessage("{\"type\":\"REQUEST_TRUMPF\"}");

        verify(endpoint).sendString("{\"type\":\"CHOOSE_TRUMPF\"," +
                "\"data\":{\"mode\":\"OBEABE\",\"trumpfColor\":null}}");
    }

}