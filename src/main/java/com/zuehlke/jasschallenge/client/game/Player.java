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

    public Move makeMove(Round round) {
        if(cards.size() == 0) throw new RuntimeException("Cannot play a card without cards in deck");
        final Card cardToPlay = cards.stream()
                .filter(card -> canPlayCard(card, round))
                .findAny()
                .orElse(cards.stream().findAny().get());
        cards.remove(cardToPlay);
        return new Move(this, cardToPlay);
    }

    public Mode decideTrumpfColor() {
        return Mode.OBEABE;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        return !(name != null ? !name.equals(player.name) : player.name != null);

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
