package com.zuehlke.jasschallenge.client.game;

import com.zuehlke.jasschallenge.client.game.cards.Card;

import java.util.EnumSet;
import java.util.Set;

public class Player {

    private final Set<Card> cards;

    public Player() {
        this(EnumSet.noneOf(Card.class));
    }

    public Player(Set<Card> cards) {
        this.cards = cards;
    }

    public boolean canPlayCard(Card card, Round round) {
        return round.getPlayedCards().isEmpty() || card.getColor() == round.getRoundColor() || !cards.stream().anyMatch(playersCard -> playersCard.getColor() == round.getRoundColor());
    }
}
