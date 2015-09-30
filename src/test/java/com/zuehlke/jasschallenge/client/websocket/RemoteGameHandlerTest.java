package com.zuehlke.jasschallenge.client.websocket;

import com.zuehlke.jasschallenge.client.game.*;
import com.zuehlke.jasschallenge.client.game.cards.Card;
import com.zuehlke.jasschallenge.client.game.mode.Mode;
import com.zuehlke.jasschallenge.client.websocket.messages.responses.ChooseCard;
import com.zuehlke.jasschallenge.client.websocket.messages.responses.ChoosePlayerName;
import com.zuehlke.jasschallenge.client.websocket.messages.responses.ChooseSession;
import com.zuehlke.jasschallenge.client.websocket.messages.responses.ChooseTrumpf;
import com.zuehlke.jasschallenge.client.websocket.messages.type.*;
import org.junit.Test;

import java.util.List;

import static com.zuehlke.jasschallenge.client.LambdaMatcher.match;
import static com.zuehlke.jasschallenge.client.websocket.messages.type.RemoteColor.*;
import static com.zuehlke.jasschallenge.client.websocket.messages.type.SessionChoice.AUTOJOIN;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class RemoteGameHandlerTest {

    @Test
    public void onRequestCard_returnsTheCardPlayedFromTheLocalPlayer() {

        final Player localPlayer = mock(Player.class);
        when(localPlayer.makeMove(any(GameSession.class))).thenReturn(new Move(localPlayer, Card.DIAMOND_ACE));
        when(localPlayer.getName()).thenReturn("local");
        final RemotePlayer remoteLocalPlayer = new RemotePlayer(0, "local");
        final RemotePlayer remoteOne = new RemotePlayer(2, "remote 1");
        final RemotePlayer remoteTwo = new RemotePlayer(1, "remote 2");
        final RemotePlayer remoteThree = new RemotePlayer(3, "remote 3");
        final List<RemoteTeam> remoteTeams = asList(
                new RemoteTeam("team a", asList(remoteOne, remoteThree)),
                new RemoteTeam("team b", asList(remoteLocalPlayer, remoteTwo)));

        final RemoteGameHandler remoteGameHandler = new RemoteGameHandler(localPlayer);
        remoteGameHandler.onBroadCastTeams(remoteTeams);
        remoteGameHandler.onBroadCastTrumpf(new TrumpfChoice(Trumpf.OBEABE, null));

        final ChooseCard chooseCard = remoteGameHandler.onRequestCard();

        assertThat(chooseCard.getData(), equalTo(new RemoteCard(14, DIAMONDS)));
    }

    @Test
    public void onRequestTrumpf_asksThePlayerForTrumpf() {

        final Player localPlayer = mock(Player.class);
        when(localPlayer.chooseTrumpf(any(GameSession.class))).thenReturn(Mode.topdown());
        when(localPlayer.getName()).thenReturn("local");
        final RemotePlayer remoteLocalPlayer = new RemotePlayer(2, "local");
        final List<RemoteTeam> remoteTeams = asList(
                new RemoteTeam("team a", asList(remoteLocalPlayer)));

        final RemoteGameHandler remoteGameHandler = new RemoteGameHandler(localPlayer);
        remoteGameHandler.onBroadCastTeams(remoteTeams);
        remoteGameHandler.onBroadCastTrumpf(new TrumpfChoice(Trumpf.OBEABE, null));

        final ChooseTrumpf chooseTrumpf = remoteGameHandler.onRequestTrumpf();

        assertThat(chooseTrumpf, equalTo(new ChooseTrumpf(Trumpf.OBEABE)));
    }

    @Test
    public void onRequestSession_returnsSessionChoice() {

        final ChooseSession chooseSession = new RemoteGameHandler(null).onRequestSessionChoice();

        assertThat(chooseSession, equalTo(new ChooseSession(AUTOJOIN, "Java client session", SessionType.TOURNAMENT)));
    }

    @Test
    public void onDealCards_setsTheCardsOnLocalPlayer() {

        final Player localPlayer = new Player("test");
        final List<RemoteCard> dealCard = asList(
                new RemoteCard(14, DIAMONDS),
                new RemoteCard(8, RemoteColor.SPADES),
                new RemoteCard(6, CLUBS));

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
        remoteGameHandler.onBroadCastTeams(remoteTeams);

        assertThat(remoteGameHandler.getTeams(), contains(
                match(team -> "team a".equals(team.getTeamName()), "team a"),
                match(team -> "team b".equals(team.getTeamName()), "team b")));
        assertThat(remoteGameHandler.getTeams(), contains(
                match(team -> team.getPlayers().size() == 2, "two members in team a"),
                match(team -> team.getPlayers().size() == 2, "two members in team b")));
        assertThat(remoteGameHandler.getTeams().get(1).getPlayers().toArray(), hasItemInArray(equalTo(localPlayer)));
    }

    @Test
    public void onBroadcastStich_startsNewRound() {
        final Player localPlayer = new Player("local");
        final RemotePlayer remoteLocalPlayer = new RemotePlayer(2, "local");
        final RemotePlayer remoteOne = new RemotePlayer(0, "remote 1");
        final RemotePlayer remoteTwo = new RemotePlayer(1, "remote 2");
        final RemotePlayer remoteThree = new RemotePlayer(3, "remote 3");
        final List<RemoteTeam> remoteTeams = asList(
                new RemoteTeam("team a", asList(remoteOne, remoteThree)),
                new RemoteTeam("team b", asList(remoteLocalPlayer, remoteTwo)));
        final RemoteGameHandler remoteGameHandler = new RemoteGameHandler(localPlayer);

        remoteGameHandler.onBroadCastTeams(remoteTeams);
        remoteGameHandler.onBroadCastTrumpf(new TrumpfChoice(Trumpf.OBEABE, null));
        remoteGameHandler.onPlayedCards(asList(new RemoteCard(13, CLUBS)));
        remoteGameHandler.onPlayedCards(asList(new RemoteCard(13, CLUBS), new RemoteCard(14, CLUBS)));
        remoteGameHandler.onBroadCastStich(new Stich("remote 2", 0, emptyList(), emptyList()));

        assertThat(remoteGameHandler.getCurrentRound().getMoves(), empty());
        assertThat(remoteGameHandler.getCurrentRound().getRoundNumber(), equalTo(1));
    }

    @Test
    public void onPlayedCards_afterStartingAGame_callsMakeMoveOnSession() {

        final Player localPlayer = mock(Player.class);
        when(localPlayer.getName()).thenReturn("Player 1");
        final GameSession session = spy(GameSessionBuilder
                .newSession()
                .withStartedGame(Mode.topdown())
                .createGameSession());
        final RemoteGameHandler handler = new RemoteGameHandler(localPlayer, session);

        handler.onPlayedCards(asList(new RemoteCard(13, CLUBS)));

        verify(session).makeMove(new Move(new Player("Player 1"), Card.CLUB_KING));
    }

    @Test
    public void onPlayedCards_roundIsUpdated() {

        final Player localPlayer = mock(Player.class);
        when(localPlayer.getName()).thenReturn("local");
        when(localPlayer.makeMove(any(GameSession.class))).thenReturn(new Move(localPlayer, Card.DIAMOND_ACE));

        final RemotePlayer remoteLocalPlayer = new RemotePlayer(2, "local");
        final RemotePlayer remoteOne = new RemotePlayer(0, "remote 1");
        final RemotePlayer remoteTwo = new RemotePlayer(1, "remote 2");
        final RemotePlayer remoteThree = new RemotePlayer(3, "remote 3");
        final List<RemoteTeam> remoteTeams = asList(
                new RemoteTeam("team a", asList(remoteOne, remoteThree)),
                new RemoteTeam("team b", asList(remoteLocalPlayer, remoteTwo)));

        final RemoteGameHandler handler = new RemoteGameHandler(localPlayer);
        handler.onBroadCastTeams(remoteTeams);
        handler.onBroadCastTrumpf(new TrumpfChoice(Trumpf.OBEABE, null));
        handler.onPlayedCards(asList(new RemoteCard(13, CLUBS)));
        handler.onPlayedCards(asList(new RemoteCard(13, CLUBS), new RemoteCard(10, DIAMONDS)));

        final Player playerOne = new Player("remote 1");
        final Player playerTwo = new Player("remote 2");
        Round expected = Round.createRound(Mode.topdown(), 0, PlayingOrder.createOrder(asList(playerOne, playerTwo)));
        expected.makeMove(new Move(playerOne, Card.CLUB_KING));
        expected.makeMove(new Move(playerTwo, Card.DIAMOND_TEN));
        assertThat(handler.getCurrentRound(), equalTo(expected));
    }

    @Test
    public void onPlayedCards_mapsPlayedCardToPlayer_startsWithLowestId() {
        final Player localPlayer = new Player("local");
        final RemotePlayer remoteLocalPlayer = new RemotePlayer(2, "local");
        final RemotePlayer remoteOne = new RemotePlayer(0, "remote 1");
        final RemotePlayer remoteTwo = new RemotePlayer(1, "remote 2");
        final RemotePlayer remoteThree = new RemotePlayer(3, "remote 3");
        final List<RemoteTeam> remoteTeams = asList(
                new RemoteTeam("team a", asList(remoteOne, remoteThree)),
                new RemoteTeam("team b", asList(remoteLocalPlayer, remoteTwo)));
        final RemoteGameHandler remoteGameHandler = new RemoteGameHandler(localPlayer);
        remoteGameHandler.onBroadCastTeams(remoteTeams);
        remoteGameHandler.onBroadCastTrumpf(new TrumpfChoice(Trumpf.OBEABE, null));

        remoteGameHandler.onPlayedCards(asList(new RemoteCard(14, DIAMONDS)));

        assertThat(remoteGameHandler.getCurrentRound().getMoves().get(0).getPlayer().getName(), equalTo(remoteOne.getName()));
    }

    @Test
    public void onPlayedCards_mapsPlayerCorrectly_startsWithTheLastWhoMadeAStich() {
        final Player localPlayer = new Player("local");
        final RemotePlayer remoteLocalPlayer = new RemotePlayer(2, "local");
        final RemotePlayer remoteOne = new RemotePlayer(0, "remote 1");
        final RemotePlayer remoteTwo = new RemotePlayer(1, "remote 2");
        final RemotePlayer remoteThree = new RemotePlayer(3, "remote 3");
        final List<RemoteTeam> remoteTeams = asList(
                new RemoteTeam("team a", asList(remoteOne, remoteThree)),
                new RemoteTeam("team b", asList(remoteLocalPlayer, remoteTwo)));
        final RemoteGameHandler remoteGameHandler = new RemoteGameHandler(localPlayer);

        remoteGameHandler.onBroadCastTeams(remoteTeams);
        remoteGameHandler.onBroadCastTrumpf(new TrumpfChoice(Trumpf.OBEABE, null));
        remoteGameHandler.onPlayedCards(asList(new RemoteCard(6, DIAMONDS)));
        remoteGameHandler.onPlayedCards(asList(new RemoteCard(6, DIAMONDS), new RemoteCard(13, CLUBS)));
        remoteGameHandler.onPlayedCards(asList(new RemoteCard(6, DIAMONDS), new RemoteCard(13, CLUBS), new RemoteCard(14, DIAMONDS)));
        remoteGameHandler.onPlayedCards(asList(new RemoteCard(6, DIAMONDS), new RemoteCard(13, CLUBS), new RemoteCard(14, DIAMONDS), new RemoteCard(11, DIAMONDS)));
        remoteGameHandler.onBroadCastStich(new Stich("local", 0, emptyList(), emptyList()));
        remoteGameHandler.onPlayedCards(asList(new RemoteCard(14, DIAMONDS)));
        remoteGameHandler.onPlayedCards(asList(new RemoteCard(14, DIAMONDS), new RemoteCard(13, CLUBS)));
        remoteGameHandler.onPlayedCards(asList(new RemoteCard(14, DIAMONDS), new RemoteCard(13, CLUBS), new RemoteCard(6, HEARTS)));
        remoteGameHandler.onPlayedCards(asList(new RemoteCard(14, DIAMONDS), new RemoteCard(13, CLUBS), new RemoteCard(6, HEARTS), new RemoteCard(11, DIAMONDS)));

        assertThat(remoteGameHandler.getCurrentRound().getMoves(), hasSize(4));
        assertThat(remoteGameHandler.getCurrentRound().getMoves().get(0).getPlayer().getName(), equalTo("local"));
        assertThat(remoteGameHandler.getCurrentRound().getMoves().get(1).getPlayer().getName(), equalTo("remote 3"));
        assertThat(remoteGameHandler.getCurrentRound().getMoves().get(2).getPlayer().getName(), equalTo("remote 1"));
        assertThat(remoteGameHandler.getCurrentRound().getMoves().get(3).getPlayer().getName(), equalTo("remote 2"));
    }
}