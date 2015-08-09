package com.zuehlke.jasschallenge.client.game.strategy;

import com.zuehlke.jasschallenge.client.game.Round;
import com.zuehlke.jasschallenge.client.game.cards.Card;

import java.util.Set;

/**
 * Created by florian on 09.08.15.
 */
public interface Strategy {
    Card calculateNextCardToPlay(Round round, Set<Card> availableCards);
}
