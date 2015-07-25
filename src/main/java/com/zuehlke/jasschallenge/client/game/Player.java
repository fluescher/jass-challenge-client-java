package com.zuehlke.jasschallenge.client.game;

import com.zuehlke.jasschallenge.client.game.cards.Card;

import java.util.EnumSet;
import java.util.Set;

public class Player {

    private final String name;
    private final Set<Card> cards;

    public Player(String name) {
        this(name, EnumSet.noneOf(Card.class));
    }

    Player(String name, Set<Card> cards) {
        this.name = name;
        this.cards = EnumSet.copyOf(cards);
    }

    Player() {
        this("unnamed");
    }

    public boolean canPlayCard(Card card, Round round) {
        return round.getPlayedCards().isEmpty()
                || card.getColor() == round.getRoundColor()
                || !cards.stream().anyMatch(playersCard -> playersCard.getColor() == round.getRoundColor());
    }

    public String getName() {
        return name;
    }

    public Set<Card> getCards() {
        return cards;
    }

    public void setCards(Set<Card> cards) {
        this.cards.clear();
        this.cards.addAll(cards);
    }
}
