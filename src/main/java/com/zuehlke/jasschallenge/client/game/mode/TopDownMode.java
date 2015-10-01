package com.zuehlke.jasschallenge.client.game.mode;

import com.zuehlke.jasschallenge.client.game.Move;
import com.zuehlke.jasschallenge.client.game.Player;
import com.zuehlke.jasschallenge.client.game.cards.Card;
import com.zuehlke.jasschallenge.client.game.cards.Color;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

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
                .mapToInt(card -> card.getValue().getScore())
                .sum();
    }

    @Override
    public Player determineWinner(List<Move> moves) {

        final Comparator<Move> moveComparator = (move, move2) -> move.getPlayedCard().isHigherThan(move2.getPlayedCard()) ? 1 : -1;

        return new GeneralRules().getWinner(moves, moveComparator);
    }

    @Override
    public boolean canPlayCard(Card card, Set<Card> alreadyPlayedCards, Color currentRoundColor, Set<Card> playerCards) {
        return new GeneralRules().canPlayCard(card, alreadyPlayedCards, currentRoundColor, playerCards);
    }

}
