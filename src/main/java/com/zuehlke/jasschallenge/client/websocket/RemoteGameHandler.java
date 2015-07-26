package com.zuehlke.jasschallenge.client.websocket;

import com.zuehlke.jasschallenge.client.game.Player;
import com.zuehlke.jasschallenge.client.game.Round;
import com.zuehlke.jasschallenge.client.game.cards.Card;
import com.zuehlke.jasschallenge.client.websocket.messages.*;
import com.zuehlke.jasschallenge.client.websocket.messages.type.RemoteCard;
import com.zuehlke.jasschallenge.client.websocket.messages.type.RemoteColor;
import com.zuehlke.jasschallenge.client.websocket.messages.type.TrumpfChoice;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static com.zuehlke.jasschallenge.client.websocket.messages.type.SessionType.AUTOJOIN;
import static com.zuehlke.jasschallenge.client.websocket.messages.type.Trumpf.OBEABE;
import static java.util.stream.Collectors.toSet;

public class RemoteGameHandler {
    private final Player localPlayer;
    private Round currentRound = Round.createRound(0);

    public RemoteGameHandler(Player localPlayer) {
        this.localPlayer = localPlayer;
    }

    public ChooseSession onRequestSessionChoice() {
        return new ChooseSession(AUTOJOIN);
    }

    public Player getLocalPlayer() {
        return localPlayer;
    }

    public Round getCurrentRound() {
        return currentRound;
    }

    public ChoosePlayerName onRequestPlayerName() {
        return new ChoosePlayerName(localPlayer.getName());
    }

    public void onDealCards(DealCard dealCard) {
        localPlayer.setCards(mapAllToCards(dealCard.getData()));
    }

    public ChooseTrumpf onRequestTrumpf() {
        return new ChooseTrumpf(OBEABE);
    }

    public ChooseCard onRequestCard() {
        return new ChooseCard(mapToRemoteCard(getLocalPlayer().getNextCard(getCurrentRound())));
    }

    public void onPlayedCards(PlayedCards playedCards) {
        this.currentRound = createRound(playedCards.getData());
    }

    public void onBroadCastStich(BroadCastStich stich) {

    }

    public void onPlayerJoined(PlayerJoined joinedPlayer) {

    }

    public void onBroadCastTeams(BroadCastTeams teams) {

    }

    public void onBroadCastTrumpf(BroadCastTrumpf trumpfChoice) {

    }

    public void onBroadGameFinished(BroadCastGameFinished gameFinished) {

    }

    public void onBroadCastWinnerTeam(BroadCastWinnerTeam winnerTeam) {

    }

    public void onRejectCard(RejectCard rejectCard) {

    }

    private static Card mapToCard(RemoteCard remoteCard) {
        return Arrays.stream(Card.values())
                .filter(card -> card.getColor() == remoteCard.getColor().getMappedColor())
                .filter(card -> card.getRank() == remoteCard.getNumber() - 5)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Unable to map card"));
    }

    private static Set<Card> mapAllToCards(List<RemoteCard> remoteCards) {
        return remoteCards.stream().map(RemoteGameHandler::mapToCard).collect(toSet());
    }

    private static RemoteCard mapToRemoteCard(Card card) {
        final RemoteColor remoteColor = Arrays.stream(RemoteColor.values())
                .filter(color -> color.getMappedColor() == card.getColor())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Could not map color"));
        return new RemoteCard(card.getRank() + 5, remoteColor);
    }

    private static Round createRound(List<RemoteCard> remoteCards) {
        final Round r = Round.createRound(0);
        remoteCards.forEach(remoteCard -> r.playCard(null, mapToCard(remoteCard)));
        return r;
    }
}
