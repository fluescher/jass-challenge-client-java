package com.zuehlke.jasschallenge.client.game.mode;

import com.zuehlke.jasschallenge.client.game.Move;
import com.zuehlke.jasschallenge.client.game.Player;
import com.zuehlke.jasschallenge.client.game.cards.Card;
import com.zuehlke.jasschallenge.client.game.cards.Color;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

class TopDownMode implements Mode {
    @Override
    public TrumpfName getTrumpfName() {
        return TrumpfName.OBEABE;
    }

    @Override
    public Color getTrumpfColor() {
        return null;
    }

    @Override
    public int calculateScore(Set<Card> playedCards) {
        return playedCards.stream()
                .mapToInt(Card::getScore)
                .sum();
    }

    @Override
    public Player determineWinner(List<Move> moves) {
        if (moves == null || moves.isEmpty()) return null;

        final Color firstCardColor = moves.get(0).getPlayedCard().getColor();
        return moves.stream()
                .filter(allCardsWithColor(firstCardColor))
                .max((move, move2) -> move.getPlayedCard().isHigherThan(move2.getPlayedCard()) ? 1 : -1)
                .map(Move::getPlayer)
                .orElse(null);
    }

    @Override
    public boolean canPlayCard(Card card, Set<Card> alreadyPlayedCards, Color currentRoundColor, Set<Card> playerCards) {
        return alreadyPlayedCards.isEmpty()
                || card.getColor() == currentRoundColor
                || !playerCards.stream().anyMatch(playersCard -> playersCard.getColor() == currentRoundColor);
    }

    private static Predicate<Move> allCardsWithColor(Color firstCardColor) {
        return move -> move.getPlayedCard().getColor() == firstCardColor;
    }
}
