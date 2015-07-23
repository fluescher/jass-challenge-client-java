package com.zuehlke.jasschallenge.client.game;

import com.zuehlke.jasschallenge.client.game.cards.Card;

public class Player {
    public boolean canPlayCard(Card card, Round round) {
        return round.getPlayedCards().isEmpty() || card.getColor() == round.getRoundColor();
    }
}
