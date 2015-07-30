package com.zuehlke.jasschallenge.client.websocket;

import com.zuehlke.jasschallenge.client.game.Move;
import com.zuehlke.jasschallenge.client.game.Player;
import com.zuehlke.jasschallenge.client.game.Round;
import com.zuehlke.jasschallenge.client.game.Team;
import com.zuehlke.jasschallenge.client.game.cards.Card;
import com.zuehlke.jasschallenge.client.websocket.messages.*;
import com.zuehlke.jasschallenge.client.websocket.messages.type.RemoteCard;
import com.zuehlke.jasschallenge.client.websocket.messages.type.RemoteColor;
import com.zuehlke.jasschallenge.client.websocket.messages.type.RemotePlayer;
import com.zuehlke.jasschallenge.client.websocket.messages.type.RemoteTeam;

import java.util.*;

import static com.zuehlke.jasschallenge.client.websocket.messages.type.SessionType.AUTOJOIN;
import static com.zuehlke.jasschallenge.client.websocket.messages.type.Trumpf.OBEABE;
import static java.lang.Integer.compare;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

public class RemoteGameHandler {
    private final Player localPlayer;
    private final List<Player> playerOrder;
    private final List<Team> teams;
    private Round currentRound = Round.createRound(0);
    private int nextStartPlayer;

    public RemoteGameHandler(Player localPlayer) {
        this.localPlayer = localPlayer;
        this.teams = new ArrayList<>();
        this.playerOrder = new ArrayList<>();
    }

    public Round getCurrentRound() {
        return currentRound;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public ChooseSession onRequestSessionChoice() {
        return new ChooseSession(AUTOJOIN);
    }

    public ChoosePlayerName onRequestPlayerName() {
        return new ChoosePlayerName(localPlayer.getName());
    }

    public void onDealCards(DealCard dealCard) {
        localPlayer.setCards(mapAllToCards(dealCard.getData()));
    }

    public void onPlayerJoined(PlayerJoined joinedPlayer) {
    }

    public void onBroadCastTeams(BroadCastTeams remoteTeams) {
        teams.addAll(remoteTeams.getData().stream()
                .map(this::toTeam)
                .collect(toList()));
        playerOrder.addAll(remoteTeams.getData().stream()
                .flatMap(remoteTeam -> remoteTeam.getPlayers().stream())
                .sorted((remotePlayer1, remotePlayer2) -> compare(remotePlayer1.getId(), remotePlayer2.getId()))
                .map(player -> findPlayerByName(player.getName()))
                .collect(toList()));
    }

    public ChooseTrumpf onRequestTrumpf() {
        return new ChooseTrumpf(OBEABE);
    }

    public void onBroadCastTrumpf(BroadCastTrumpf trumpfChoice) {

    }

    public ChooseCard onRequestCard() {
        final Move move = localPlayer.makeMove(getCurrentRound());
        final RemoteCard cardToPlay = mapToRemoteCard(move.getPlayedCard());
        return new ChooseCard(cardToPlay);
    }

    public void onPlayedCards(PlayedCards playedCards) {
        final int playerPosition = playedCards.getData().size() - 1;
        final RemoteCard remoteCard = playedCards.getData().get(playerPosition);
        final Player player = getPlayerAtPosition(getBoundIndex(playerPosition));
        final Move move = new Move(player, mapToCard(remoteCard));
        this.currentRound.makeMove(move);
    }

    private int getBoundIndex(int playerPosition) {
        return (this.nextStartPlayer + playerPosition) % 4;
    }

    private Player getPlayerAtPosition(int playerPosition) {
        if(playerOrder.size() <= playerPosition) return null;

        return playerOrder.get(playerPosition);
    }

    public void onBroadCastStich(BroadCastStich stich) {
        final Player winner = findPlayerByName(stich.getData().getName());
        final int winnerIndex = playerOrder.indexOf(winner);
        this.nextStartPlayer = winnerIndex;
        this.currentRound = Round.createRound(this.currentRound.getRoundNumber()+1);
    }

    public void onBroadGameFinished(BroadCastGameFinished gameFinished) {

    }

    public void onBroadCastWinnerTeam(BroadCastWinnerTeam winnerTeam) {

    }

    public void onRejectCard(RejectCard rejectCard) {
        throw new RuntimeException("Card was rejected");
    }

    private Player findPlayerByName(String name) {
        return teams.stream()
                .flatMap(team -> team.getPlayers().stream())
                .filter(player -> player.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No Player with name " + name + " found"));
    }

    private Team toTeam(RemoteTeam remoteTeam) {
        final List<Player> players = remoteTeam.getPlayers().stream().map(this::mapPlayer).collect(toList());
        return new Team(remoteTeam.getName(), players);
    }

    private Player mapPlayer(RemotePlayer remotePlayer) {
        final Player player = new Player(remotePlayer.getName());
        if (player.getName().equals(localPlayer.getName())) {
            return localPlayer;
        } else {
            return player;
        }
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

    private static Round createRound(List<RemoteCard> remoteCards) {
        final Round r = Round.createRound(0);
        remoteCards.forEach(remoteCard -> r.makeMove(new Move(null, mapToCard(remoteCard))));
        return r;
    }
}
