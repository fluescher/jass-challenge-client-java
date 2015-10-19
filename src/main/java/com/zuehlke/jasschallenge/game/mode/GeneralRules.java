package com.zuehlke.jasschallenge.game.mode;

import com.zuehlke.jasschallenge.client.game.Move;
import com.zuehlke.jasschallenge.game.cards.Card;
import com.zuehlke.jasschallenge.game.cards.Color;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

class GeneralRules {

    public static final int LAST_ROUND_BONUS = 5;

    public int calculateLastRoundBonus(int factor) {
        return factor * LAST_ROUND_BONUS;
    }

    public boolean canPlayCard(Card card, Set<Card> alreadyPlayedCards, Color currentRoundColor, Set<Card> playerCards) {
        return alreadyPlayedCards.isEmpty()
                || card.getColor() == currentRoundColor
                || !playerCards.stream().anyMatch(playersCard -> playersCard.getColor() == currentRoundColor);
    }

    public Move determineWinnerMove(List<Move> moves, Comparator<Move> moveComparator) {
        if (moves == null || moves.isEmpty()) return null;

        final Color firstCardColor = moves.get(0).getPlayedCard().getColor();
        return moves.stream()
                .filter(allCardsWithColor(firstCardColor))
                .max(moveComparator)
                .orElse(null);
    }

    private static Predicate<Move> allCardsWithColor(Color firstCardColor) {
        return move -> move.getPlayedCard().getColor() == firstCardColor;
    }

}
