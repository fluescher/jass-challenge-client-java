package com.zuehlke.jasschallenge.game.mode;

import com.zuehlke.jasschallenge.client.game.Move;
import com.zuehlke.jasschallenge.game.Trumpf;
import com.zuehlke.jasschallenge.game.cards.Card;
import com.zuehlke.jasschallenge.game.cards.Color;

import java.util.*;

public abstract class Mode {

    public static Mode topDown() { return new TopDownMode(); }
    public static Mode bottomUp() { return new BottomUpMode(); }
    static Mode trump(Color color) { return new TrumpfColorMode(color); }
    public static Mode shift() { return new ShiftMode(); }
    public static Mode from(Trumpf trumpf, Color trumpfColor) {
        switch (trumpf) {
            case UNDEUFE:
                return bottomUp();
            case OBEABE:
                return topDown();
            case SCHIEBE:
                return shift();
            default:
                return trump(trumpfColor);
        }
    }

    public static List<Mode> standardModes() {
        List<Mode> modes = new LinkedList<>();
        modes.add(topDown());
        modes.add(bottomUp());
        modes.add(trump(Color.CLUBS));
        modes.add(trump(Color.DIAMONDS));
        modes.add(trump(Color.HEARTS));
        modes.add(trump(Color.SPADES));
        return modes;
    }

    public abstract int calculateRoundScore(int roundNumber, Set<Card> playedCards);

    public abstract Trumpf getTrumpfName();

    public abstract Color getTrumpfColor();

    public abstract int calculateScore(Set<Card> playedCards);

    public Move determineWinningMove(List<Move> moves) {
        Comparator<Card> rankComparator = createRankComparator();

        final Comparator<Move> moveComparator = (move, move2) -> rankComparator.compare(move.getPlayedCard(), move2.getPlayedCard());

        return GeneralRules.determineWinnerMove(moves, moveComparator, Optional.<Color> ofNullable(getTrumpfColor()));
    }

    public abstract boolean canPlayCard(Card card, Set<Card> alreadyPlayedCards, Color currentRoundColor, Set<Card> playerCards);

    public abstract int getFactor();

    public abstract Comparator<Card> createRankComparator();
}
