package com.zuehlke.jasschallenge.client.game.mode;

import com.zuehlke.jasschallenge.client.game.Move;
import com.zuehlke.jasschallenge.client.game.cards.Card;
import com.zuehlke.jasschallenge.client.game.cards.Color;
import com.zuehlke.jasschallenge.client.websocket.messages.type.Trumpf;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public interface Mode {

    static Mode topDown() { return new TopDownMode(); }
    static Mode bottomUp() { return new BottomUpMode(); }
    static Mode trump(Color color) { return new TrumpfColorMode(color); }
    static Mode shift() { return new ShiftMode(); }
    static List<Mode> standardModes() {
        List<Mode> modes = new LinkedList<>();
        modes.add(topDown());
        modes.add(bottomUp());
        modes.add(trump(Color.CLUBS));
        modes.add(trump(Color.DIAMONDS));
        modes.add(trump(Color.HEARTS));
        modes.add(trump(Color.SPADES));
        return modes;
    }

    int calculateRoundScore(int roundNumber, Set<Card> playedCards);

    Trumpf getTrumpfName();

    Color getTrumpfColor();

    int calculateScore(Set<Card> playedCards);

    Move determineWinningMove(List<Move> moves);

    boolean canPlayCard(Card card, Set<Card> alreadyPlayedCards, Color currentRoundColor, Set<Card> playerCards);

    int getFactor();
}
