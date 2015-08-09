package com.zuehlke.jasschallenge.client.websocket;

import com.zuehlke.jasschallenge.client.game.*;
import com.zuehlke.jasschallenge.client.game.cards.Card;
import com.zuehlke.jasschallenge.client.websocket.messages.PlayerJoinedSession;
import com.zuehlke.jasschallenge.client.websocket.messages.responses.ChooseCard;
import com.zuehlke.jasschallenge.client.websocket.messages.responses.ChoosePlayerName;
import com.zuehlke.jasschallenge.client.websocket.messages.responses.ChooseSession;
import com.zuehlke.jasschallenge.client.websocket.messages.responses.ChooseTrumpf;
import com.zuehlke.jasschallenge.client.websocket.messages.type.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;

import static com.zuehlke.jasschallenge.client.websocket.messages.type.SessionType.AUTOJOIN;
import static com.zuehlke.jasschallenge.client.websocket.messages.type.Trumpf.OBEABE;
import static java.lang.Integer.compare;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

public class RemoteGameHandler {
    private final Player localPlayer;
    private GameSession gameSession;

    private final PlayerMapper playerMapper;

    private final static Logger logger = LoggerFactory.getLogger(RemoteGameHandler.class);

    public RemoteGameHandler(Player localPlayer) {
        this.localPlayer = localPlayer;
        this.playerMapper = new PlayerMapper(localPlayer);
    }

    Round getCurrentRound() {
        return gameSession.getCurrentRound();
    }

    List<Team> getTeams() {
        return gameSession.getTeams();
    }

    public ChooseSession onRequestSessionChoice() {
        return new ChooseSession(AUTOJOIN);
    }

    public ChoosePlayerName onRequestPlayerName() {
        return new ChoosePlayerName(localPlayer.getName());
    }

    public void onDealCards(List<RemoteCard> dealCard) {
        localPlayer.setCards(mapAllToCards(dealCard));
    }

    public void onPlayerJoined(PlayerJoinedSession joinedPlayer) {
    }

    public void onBroadCastTeams(List<RemoteTeam> remoteTeams) {
        final List<Team> teams = mapTeams(remoteTeams);
        final List<Player> playersInPlayingOrder = getPlayersInPlayingOrder(remoteTeams);
        gameSession = new GameSession(teams, playersInPlayingOrder);
    }

    public ChooseTrumpf onRequestTrumpf() {
        return new ChooseTrumpf(OBEABE);
    }

    public void onBroadCastTrumpf(TrumpfChoice trumpfChoice) {
        gameSession.startNewGame(Mode.valueOf(trumpfChoice.getMode().name()));
    }

    public ChooseCard onRequestCard() {
        checkEquals(getCurrentRound().getPlayingOrder().getCurrentPlayer(), localPlayer, "Order differed between remote and local state");

        final Move move = localPlayer.makeMove(getCurrentRound());
        final RemoteCard cardToPlay = mapToRemoteCard(move.getPlayedCard());

        return new ChooseCard(cardToPlay);
    }

    public void onPlayedCards(List<RemoteCard> playedCards) {
        final int playerPosition = playedCards.size() - 1;
        final RemoteCard remoteCard = playedCards.get(playerPosition);

        final Player player = getCurrentRound().getPlayingOrder().getCurrentPlayer();
        getCurrentRound().getPlayingOrder().moveToNextPlayer();

        final Move move = new Move(player, mapToCard(remoteCard));
        getCurrentRound().makeMove(move);
    }

    public void onBroadCastStich(Stich stich) {
        final Player winner = playerMapper.findPlayerByName(stich.getName());
        checkEquals(winner, getCurrentRound().getWinner(), "Local winner differs from remote");

        gameSession.startNextRound();
    }

    public void onBroadGameFinished(List<RemoteTeam> remoteTeams) {
    }

    public void onBroadCastWinnerTeam(RemoteTeam winnerTeam) {

    }

    public void onRejectCard(RemoteCard rejectCard) {
        throw new RuntimeException("Card was rejected");
    }

    private List<Team> mapTeams(List<RemoteTeam> remoteTeams) {
        return remoteTeams.stream()
                .map(this::toTeam)
                .collect(toList());
    }

    private List<Player> getPlayersInPlayingOrder(List<RemoteTeam> remoteTeams) {
        return remoteTeams.stream()
                .flatMap(remoteTeam -> remoteTeam.getPlayers().stream())
                .sorted((remotePlayer1, remotePlayer2) -> compare(remotePlayer1.getId(), remotePlayer2.getId()))
                .map(player -> playerMapper.findPlayerByName(player.getName()))
                .collect(toList());
    }

    private Team toTeam(RemoteTeam remoteTeam) {
        final List<Player> players = remoteTeam.getPlayers().stream().map(playerMapper::mapPlayer).collect(toList());
        return new Team(remoteTeam.getName(), players);
    }

    private static Card mapToCard(RemoteCard remoteCard) {
        return stream(Card.values())
                .filter(card -> card.getColor() == remoteCard.getColor().getMappedColor())
                .filter(card -> card.getRank() == remoteCard.getNumber() - 5)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Unable to map card"));
    }

    private static Set<Card> mapAllToCards(List<RemoteCard> remoteCards) {
        return remoteCards.stream().map(RemoteGameHandler::mapToCard).collect(toSet());
    }

    private static RemoteCard mapToRemoteCard(Card card) {
        final RemoteColor remoteColor = stream(RemoteColor.values())
                .filter(color -> color.getMappedColor() == card.getColor())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Could not map color"));
        return new RemoteCard(card.getRank() + 5, remoteColor);
    }

    private static void checkEquals(Object a, Object b, String errorMessage) {
        if(!a.equals(b)) {
            logger.warn("Expected {} to be equal to {}: {}", a, b, errorMessage);
            throw new RuntimeException(errorMessage);
        }
    }

}
