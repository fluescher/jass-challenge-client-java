package com.zuehlke.jasschallenge.client.game.mode;

import com.zuehlke.jasschallenge.client.game.Game;
import com.zuehlke.jasschallenge.client.game.Move;
import com.zuehlke.jasschallenge.client.game.cards.Card;
import com.zuehlke.jasschallenge.client.game.cards.CardValue;
import com.zuehlke.jasschallenge.client.game.cards.Color;
import com.zuehlke.jasschallenge.client.websocket.messages.type.Trumpf;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import static java.lang.String.valueOf;

class TrumpfColorMode implements Mode {

    private final Color trumpfColor;
    private final GeneralRules generalRules = new GeneralRules();

    public TrumpfColorMode(Color trumpfColor) {

        this.trumpfColor = trumpfColor;
    }

    @Override
    public Trumpf getTrumpfName() {
        return Trumpf.TRUMPF;
    }

    @Override
    public Color getTrumpfColor() {
        return trumpfColor;
    }

    @Override
    public int calculateRoundScore(int roundNumber, Set<Card> playedCards) {
        if(roundNumber == Game.LAST_ROUND_NUMBER) {
            return generalRules.calculateLastRoundBonus(getFactor()) + calculateScore(playedCards);
        }
        return calculateScore(playedCards);
    }

    @Override
    public int calculateScore(Set<Card> playedCards) {
        return getFactor() * playedCards.stream()
                .mapToInt(card -> {
                    if(card.getValue() == CardValue.EIGHT) return 0;
                    if (isTrumpf(card)) {
                        return card.getValue().getTrumpfScore();
                    } else {
                        return card.getValue().getScore();
                    }
                }).sum();
    }

    @Override
    public Move determineWinningMove(List<Move> moves) {
        if (moves == null || moves.isEmpty()) return null;

        final Color firstCardColor = moves.get(0).getPlayedCard().getColor();
        return moves.stream()
                .filter(allCardsWithColorOrTrumpf(firstCardColor))
                .max(this::compareWithTrumpf)
                .orElse(null);
    }

    @Override
    public boolean canPlayCard(Card card, Set<Card> alreadyPlayedCards, Color currentRoundColor, Set<Card> playerCards) {
        final boolean noCardsHaveBeenPlayed = alreadyPlayedCards.isEmpty();
        final boolean hasOtherCardsOfRoundColor = playerCards.stream().anyMatch(playersCard -> playersCard.getColor() == currentRoundColor);
        final boolean isHighestTrumpfInRound = isTrumpf(card) && isHighestTrumpf(card, alreadyPlayedCards);

        if(noCardsHaveBeenPlayed) return true;
        if(hasOnlyTrumpf(playerCards)) return true;
        if(isTrumpf(card) && currentRoundColor != trumpfColor) return isHighestTrumpfInRound;
        if(currentRoundColor == trumpfColor && hasOnlyJackOfTrumpf(playerCards)) return true;
        else return !hasOtherCardsOfRoundColor || card.getColor() == currentRoundColor;
    }

    @Override
    public int getFactor() {
        if(trumpfColor == Color.SPADES || trumpfColor == Color.CLUBS) return 2;
        return 1;
    }

    @Override
    public String toString() {
        return valueOf(getTrumpfName()) + " - " +  valueOf(getTrumpfColor());
    }

    private boolean hasOnlyJackOfTrumpf(Set<Card> playerCards) {
        return playerCards.stream()
                .filter(cards -> cards.getColor() == trumpfColor)
                .allMatch(card -> card.getValue() == CardValue.JACK);
    }

    private boolean hasOnlyTrumpf(Set<Card> cards) {
        return cards.stream().allMatch(card -> card.getColor() == trumpfColor);
    }

    private boolean isHighestTrumpf(Card card, Set<Card> alreadyPlayedCards) {
        return highestTrumpf(alreadyPlayedCards)
                .map(card::isHigherTrumpfThan)
                .orElse(true);
    }

    private Optional<Card> highestTrumpf(Set<Card> alreadyPlayedCards) {
        return alreadyPlayedCards.stream()
                .filter(card -> card.getColor() == trumpfColor)
                .max(this::compareTrumpf);
    }

    private boolean isTrumpf(Card card) {
        return card.getColor() == trumpfColor;
    }

    private int compareWithTrumpf(Move move1, Move move2) {
        if (isTrumpf(move1.getPlayedCard()) && isTrumpf(move2.getPlayedCard())) {
            return compareTrumpf(move1.getPlayedCard(), move2.getPlayedCard());
        } else if (isTrumpf(move1.getPlayedCard())) {
            return 1;
        } else if (isTrumpf(move2.getPlayedCard())) {
            return -1;
        } else {
            return compareMoves(move1, move2);
        }
    }

    private int compareTrumpf(Card first, Card second) {
        return first.isHigherTrumpfThan(second) ? 1 : -1;
    }

    private int compareMoves(Move move1, Move move2) {
        return move1.getPlayedCard().isHigherThan(move2.getPlayedCard()) ? 1 : -1;
    }

    private Predicate<Move> allCardsWithColorOrTrumpf(Color firstCardColor) {
        return move ->
                move.getPlayedCard().getColor() == firstCardColor
                        || isTrumpf(move.getPlayedCard());
    }
}
