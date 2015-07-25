package com.zuehlke.jasschallenge.client.game;

import com.zuehlke.jasschallenge.client.game.cards.Card;
import com.zuehlke.jasschallenge.client.game.cards.Color;

import java.util.EnumSet;
import java.util.Set;

public class Round {
    private final int roundNumber;
    private final Set<Card> playedCards;
    private Color roundColor;

    public Round(int roundNumber) {
        this(roundNumber, EnumSet.noneOf(Card.class));
    }

    Round(int roundNumber, Set<Card> playedCards) {
        this.roundNumber = roundNumber;
        this.playedCards = EnumSet.noneOf(Card.class);
        playedCards.forEach(this::playCard);
    }

    private void playCard(Card card) {
        if(playedCards.isEmpty()) {
            roundColor = card.getColor();
        }
        playedCards.add(card);
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public Set<Card> getPlayedCards() {
        return playedCards;
    }

    public Color getRoundColor() {
        return this.roundColor;
    }
}
