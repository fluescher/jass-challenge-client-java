package com.zuehlke.jasschallenge.client.game;

import com.zuehlke.jasschallenge.client.game.cards.Card;
import com.zuehlke.jasschallenge.client.game.cards.Color;

import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;

public class Round {
    private final int roundNumber;
    private final Set<Card> playedCards;
    private Color roundColor;
    private Player winner;

    public static Round createRound(int roundNumber) {
        return new Round(roundNumber);
    }

    static Round createRoundWithCardsPlayed(int roundNumber, Set<Card> playedCards) {
        return new Round(roundNumber, playedCards);
    }

    private Round(int roundNumber) {
        this(roundNumber, EnumSet.noneOf(Card.class));
    }

    private Round(int roundNumber, Set<Card> playedCards) {
        this.roundNumber = roundNumber;
        this.playedCards = EnumSet.noneOf(Card.class);
        playedCards.forEach(card -> playCard(new Player(), card));
    }

    public void playCard(Player player, Card card) {
        if(playedCards.size() == 4) {
            throw new RuntimeException("Only four card can be played in a round");
        }
        if(playedCards.isEmpty()) {
            roundColor = card.getColor();
        }
        if(isHighestCard(card)) {
            winner = player;
        }
        playedCards.add(card);
    }

    private boolean isHighestCard(Card card) {
        if(card.getColor() != roundColor) return false;

        final Optional<Card> highestCardPlayed = getHighestCardSoFar();

        return highestCardPlayed
                .map(currentHighest -> card.isHigherThan(currentHighest))
                .orElse(true);
    }

    private Optional<Card> getHighestCardSoFar() {
        return playedCards
                        .stream()
                        .filter(playedCard -> playedCard.getColor() == roundColor)
                        .max((card1, card2) -> card1.isHigherThan(card2) ? 1 : -1);
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public Set<Card> getPlayedCards() {
        return playedCards;
    }

    public Color getRoundColor() {
        return roundColor;
    }

    public int getValue() {
        return playedCards.stream()
                          .mapToInt(Card::getScore)
                          .sum();
    }

    public Player getWinner() {
        return winner;
    }
}
