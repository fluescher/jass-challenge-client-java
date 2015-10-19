package com.zuehlke.jasschallenge.game.mode;

import com.zuehlke.jasschallenge.client.game.Move;
import com.zuehlke.jasschallenge.game.cards.Card;
import com.zuehlke.jasschallenge.game.cards.Color;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

class GeneralRules {

    public static final int LAST_ROUND_BONUS = 5;

    public static int calculateLastRoundBonus(int factor) {
        return factor * LAST_ROUND_BONUS;
    }

    public static boolean canPlayCard(Card card, Set<Card> alreadyPlayedCards, Color currentRoundColor, Set<Card> playerCards) {
        return alreadyPlayedCards.isEmpty()
                || card.getColor() == currentRoundColor
                || !playerCards.stream().anyMatch(playersCard -> playersCard.getColor() == currentRoundColor);
    }

    public static Move determineWinnerMove(List<Move> moves, Comparator<Move> moveComparator, Optional<Color> trumpfColor) {
        if (moves == null || moves.isEmpty()) return null;

        final Color firstCardColor = moves.get(0).getPlayedCard().getColor();
        return moves.stream()
                .filter(allCardsWithColorOrTrumpfColor(firstCardColor, trumpfColor))
                .max(moveComparator)
                .orElse(null);
    }

    private static Predicate<Move> allCardsWithColorOrTrumpfColor(Color firstCardColor, Optional<Color> trumpfColor) {
        return move -> move.getPlayedCard().getColor() == trumpfColor.orElse(firstCardColor) || move.getPlayedCard().getColor() == firstCardColor;
    }

}
