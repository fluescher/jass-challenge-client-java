package com.zuehlke.jasschallenge.client.websocket;

import com.zuehlke.jasschallenge.client.LambdaMatcher;
import com.zuehlke.jasschallenge.client.game.Move;
import com.zuehlke.jasschallenge.client.game.Player;
import com.zuehlke.jasschallenge.client.game.Round;
import com.zuehlke.jasschallenge.client.game.Team;
import com.zuehlke.jasschallenge.client.game.cards.Card;
import com.zuehlke.jasschallenge.client.websocket.messages.*;
import com.zuehlke.jasschallenge.client.websocket.messages.type.*;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static com.zuehlke.jasschallenge.client.LambdaMatcher.match;
import static com.zuehlke.jasschallenge.client.game.Mode.OBEABE;
import static com.zuehlke.jasschallenge.client.websocket.messages.type.SessionType.AUTOJOIN;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RemoteGameHandlerTest {

    @Test
    public void onRequestCard_returnsTheCardPlayedFromTheLocalPlayer() {

        final Player localPlayer = mock(Player.class);
        when(localPlayer.makeMove(any(Round.class))).thenReturn(new Move(localPlayer, Card.DIAMOND_ACE));

        final ChooseCard chooseCard = new RemoteGameHandler(localPlayer).onRequestCard();

        assertThat(chooseCard.getData(), equalTo(new RemoteCard(14, RemoteColor.DIAMONDS)));
    }

    @Test
    public void onCardsPlayed_roundIsUpdated() {

        final Player localPlayer = mock(Player.class);
        when(localPlayer.makeMove(any(Round.class))).thenReturn(new Move(localPlayer, Card.DIAMOND_ACE));

        final RemoteGameHandler handler = new RemoteGameHandler(localPlayer);
        handler.onPlayedCards(new PlayedCards(asList(new RemoteCard(13, RemoteColor.CLUBS), new RemoteCard(10, RemoteColor.DIAMONDS))));

        Round expected = Round.createRound(0);
        expected.playCard(null, Card.CLUB_KING);
        expected.playCard(null, Card.DIAMOND_TEN);
        assertThat(handler.getCurrentRound(), equalTo(expected));
    }

    @Test
    public void onRequestTrumpf_asksThePlayerForTrumpf() {

        final Player localPlayer = mock(Player.class);
        when(localPlayer.decideTrumpfColor()).thenReturn(OBEABE);

        final ChooseTrumpf chooseTrumpf = new RemoteGameHandler(localPlayer).onRequestTrumpf();

        assertThat(chooseTrumpf, equalTo(new ChooseTrumpf(Trumpf.OBEABE)));
    }

    @Test
    public void onRequestSession_returnsThSessionChoice() {

        final ChooseSession chooseSession = new RemoteGameHandler(null).onRequestSessionChoice();

        assertThat(chooseSession, equalTo(new ChooseSession(AUTOJOIN)));
    }

    @Test
    public void onDealCards_setsTheCardsOnLocalPlayer() {

        final Player localPlayer = new Player("test");
        final DealCard dealCard = new DealCard(asList(
                new RemoteCard(14, RemoteColor.DIAMONDS),
                new RemoteCard(8, RemoteColor.SPADES),
                new RemoteCard(6, RemoteColor.CLUBS)));

        new RemoteGameHandler(localPlayer).onDealCards(dealCard);

        assertThat(localPlayer.getCards(), containsInAnyOrder(Card.DIAMOND_ACE, Card.SPADE_EIGHT, Card.CLUB_SIX));
    }

    @Test
    public void onRequestPlayerName_repliesWithNameOfLocalPlayer() {

        final Player localPlayer = new Player("test");

        final ChoosePlayerName choosePlayerName = new RemoteGameHandler(localPlayer).onRequestPlayerName();

        assertThat(choosePlayerName, equalTo(new ChoosePlayerName("test")));
    }

    @Test
    public void onBroadCastTeam_storesTeams() {

        final Player localPlayer = new Player("local");
        final RemotePlayer remoteLocalPlayer = new RemotePlayer(2, "local");
        final RemotePlayer remoteOne = new RemotePlayer(0, "remote 1");
        final RemotePlayer remoteTwo = new RemotePlayer(1, "remote 2");
        final RemotePlayer remoteThree = new RemotePlayer(3, "remote 3");
        final List<RemoteTeam> remoteTeams = asList(
                new RemoteTeam("team a", asList(remoteOne, remoteThree)),
                new RemoteTeam("team b", asList(remoteLocalPlayer, remoteTwo)));

        final RemoteGameHandler remoteGameHandler = new RemoteGameHandler(localPlayer);
        remoteGameHandler.onBroadCastTeams(new BroadCastTeams(remoteTeams));

        assertThat(remoteGameHandler.getTeams(), contains(
                match(team -> "team a".equals(team.getTeamName()), "team a"),
                match(team -> "team b".equals(team.getTeamName()), "team b")));
        assertThat(remoteGameHandler.getTeams(), contains(
                match(team -> team.getPlayers().size() == 2, "two members in team a"),
                match(team -> team.getPlayers().size() == 2, "two members in team b")));
        assertThat(remoteGameHandler.getTeams().get(1).getPlayers().toArray(), hasItemInArray(equalTo(localPlayer)));
    }
}