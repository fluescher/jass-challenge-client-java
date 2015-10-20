package com.zuehlke.jasschallenge.localserver;

import com.zuehlke.jasschallenge.game.Trumpf;
import com.zuehlke.jasschallenge.game.cards.Card;
import com.zuehlke.jasschallenge.game.cards.Color;
import com.zuehlke.jasschallenge.game.mode.Mode;
import com.zuehlke.jasschallenge.messages.type.RemoteCard;
import com.zuehlke.jasschallenge.messages.type.RemoteColor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class StichResult {

    private List<Card> playedCardsInOrder;
    private final Map<Card, Player> playedCards;
    private final Trumpf trumpf;
    private final Color trumpfColor;
    private final int roundNumber;


    StichResult(List<Card> playedCardsInOrder, Map<Card, Player> playedCards, Trumpf trumpf, Color trumpfColor, int roundNumber) {
        this.playedCardsInOrder = playedCardsInOrder;
        this.playedCards = playedCards;
        this.trumpf = trumpf;
        this.trumpfColor = trumpfColor;
        this.roundNumber = roundNumber;
    }

    public int getPoints() {
        return Mode.from(trumpf, trumpfColor).calculateRoundScore(roundNumber, playedCards.keySet());
    }

    public Player getStichPlayer() {
        Mode mode = Mode.from(trumpf, trumpfColor);
        Card card = mode.determineWinningCard(playedCardsInOrder);
        return playedCards.get(card);
    }

    public List<RemoteCard> getPlayedCards() {
        return playedCards.keySet().stream().map(card -> new RemoteCard(card.getValue().getRank() + 5, RemoteColor.from(card.getColor()))).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "StichResult{" +
                "playedCards=" + playedCards +
                ", trumpf=" + trumpf +
                ", trumpfColor=" + trumpfColor +
                ", roundNumber=" + roundNumber +
                '}';
    }
}
