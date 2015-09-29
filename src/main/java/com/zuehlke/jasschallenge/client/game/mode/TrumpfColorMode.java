package com.zuehlke.jasschallenge.client.game.mode;

import com.zuehlke.jasschallenge.client.game.Move;
import com.zuehlke.jasschallenge.client.game.Player;
import com.zuehlke.jasschallenge.client.game.cards.Card;
import com.zuehlke.jasschallenge.client.game.cards.Color;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

class TrumpfColorMode implements Mode {

    private final Color trumpfColor;

    public TrumpfColorMode(Color trumpfColor) {

        this.trumpfColor = trumpfColor;
    }

    @Override
    public TrumpfName getTrumpfName() {
        return TrumpfName.TRUMPF;
    }

    @Override
    public Color getTrumpfColor() {
        return trumpfColor;
    }

    @Override
    public int calculateScore(Set<Card> playedCards) {
        return playedCards.stream()
                .mapToInt(card -> {
                    if (isTrumpf(card)) {
                        return card.getTrumpfScore();
                    } else {
                        return card.getScore();
                    }
                }).sum();
    }

    @Override
    public Player determineWinner(List<Move> moves) {
        if (moves == null || moves.isEmpty()) return null;

        final Color firstCardColor = moves.get(0).getPlayedCard().getColor();
        return moves.stream()
                .filter(allCardsWithColorOrTrumpf(firstCardColor))
                .max(this::compareWithTrumpf)
                .map(Move::getPlayer)
                .orElse(null);
    }

    @Override
    public boolean canPlayCard(Card card, Set<Card> alreadyPlayedCards, Color currentRoundColor, Set<Card> playerCards) {
        return alreadyPlayedCards.isEmpty()
                || card.getColor() == currentRoundColor
                || !playerCards.stream().anyMatch(playersCard -> playersCard.getColor() == currentRoundColor);
    }

    private boolean isTrumpf(Card card) {
        return card.getColor() == trumpfColor;
    }

    private int compareWithTrumpf(Move move1, Move move2) {
        if (isTrumpf(move1.getPlayedCard()) && isTrumpf(move2.getPlayedCard())) {
            return compareTrumpfMoves(move1, move2);
        } else if (isTrumpf(move1.getPlayedCard())) {
            return 1;
        } else if (isTrumpf(move2.getPlayedCard())) {
            return -1;
        } else {
            return compareMoves(move1, move2);
        }
    }

    private int compareTrumpfMoves(Move move1, Move move2) {
        return move1.getPlayedCard().getTrumpfRank() > move2.getPlayedCard().getTrumpfRank() ? 1 : -1;
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
