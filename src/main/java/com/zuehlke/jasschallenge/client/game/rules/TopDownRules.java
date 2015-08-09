package com.zuehlke.jasschallenge.client.game.rules;

import com.zuehlke.jasschallenge.client.game.Move;
import com.zuehlke.jasschallenge.client.game.Player;
import com.zuehlke.jasschallenge.client.game.cards.Card;
import com.zuehlke.jasschallenge.client.game.cards.Color;

import java.util.List;
import java.util.Set;

public class TopDownRules implements Rules {

    @Override
    public int calculateScore(Set<Card> playedCards) {
        return playedCards.stream()
                          .mapToInt(Card::getScore)
                          .sum();
    }

    @Override
    public Player determineWinner(List<Move> moves, Color roundColor) {
        return moves.stream()
                .filter(move -> move.getPlayedCard().getColor() == roundColor)
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

}
