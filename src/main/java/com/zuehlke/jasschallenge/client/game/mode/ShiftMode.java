package com.zuehlke.jasschallenge.client.game.mode;

import com.zuehlke.jasschallenge.client.game.Move;
import com.zuehlke.jasschallenge.client.game.cards.Card;
import com.zuehlke.jasschallenge.client.game.cards.Color;
import com.zuehlke.jasschallenge.client.websocket.messages.type.Trumpf;

import java.util.List;
import java.util.Set;

public class ShiftMode implements Mode {

    @Override
    public int calculateRoundScore(int roundNumber, Set<Card> playedCards) {
        return 0;
    }

    @Override
    public Trumpf getTrumpfName() {
        return Trumpf.SCHIEBE;
    }

    @Override
    public Color getTrumpfColor() {
        return null;
    }

    @Override
    public int calculateScore(Set<Card> playedCards) {
        return 0;
    }

    @Override
    public Move determineWinningMove(List<Move> moves) {
        return null;
    }

    @Override
    public boolean canPlayCard(Card card, Set<Card> alreadyPlayedCards, Color currentRoundColor, Set<Card> playerCards) {
        return false;
    }

    @Override
    public int getFactor() {
        return 0;
    }
}
