package com.zuehlke.jasschallenge.client.game.strategy;

import com.zuehlke.jasschallenge.client.game.Round;
import com.zuehlke.jasschallenge.client.game.cards.Card;
import com.zuehlke.jasschallenge.client.game.rules.TopDownRules;

import java.util.Set;

public class RandomMoveStrategy implements Strategy {
    @Override
    public Card calculateNextCardToPlay(Round round, Set<Card> availableCards) {
        return availableCards.stream()
                    .filter(card -> new TopDownRules().canPlayCard(card, round.getPlayedCards(), round.getRoundColor(), availableCards))
                    .findAny()
                    .orElse(availableCards.stream().findAny().get());
    }
}
