package com.zuehlke.jasschallenge.client.game;

import com.zuehlke.jasschallenge.client.game.cards.Card;
import com.zuehlke.jasschallenge.client.game.cards.Color;

import java.util.ArrayList;
import java.util.List;

public class Round {
    private final int roundNumber;
    private final List<Card> playedCards;
    private Color roundColor;

    public Round(int roundNumber) {
        this(roundNumber, new ArrayList<>());
    }

    Round(int roundNumber, List<Card> playedCards) {
        this.roundNumber = roundNumber;
        this.playedCards = new ArrayList<>();
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

    public List<Card> getPlayedCards() {
        return playedCards;
    }

    public Color getRoundColor() {
        return this.roundColor;
    }
}
