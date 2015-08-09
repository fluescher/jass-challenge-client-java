package com.zuehlke.jasschallenge.client.game.rules;

import com.zuehlke.jasschallenge.client.game.Move;
import com.zuehlke.jasschallenge.client.game.Player;
import com.zuehlke.jasschallenge.client.game.Round;
import com.zuehlke.jasschallenge.client.game.cards.Card;
import com.zuehlke.jasschallenge.client.game.cards.Color;

import java.util.List;
import java.util.Set;

public interface Rules {
    int calculateScore(Set<Card> playedCards);

    Player determineWinner(List<Move> moves, Color roundColor);

    boolean canPlayCard(Card card, Set<Card> alreadyPlayedCards, Color currentRoundColor, Set<Card> playerCards);
}
