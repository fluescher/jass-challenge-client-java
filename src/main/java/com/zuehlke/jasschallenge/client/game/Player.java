package com.zuehlke.jasschallenge.client.game;

import com.zuehlke.jasschallenge.client.game.cards.Card;
import com.zuehlke.jasschallenge.client.game.strategy.RandomMoveStrategy;
import com.zuehlke.jasschallenge.client.game.strategy.Strategy;
import com.zuehlke.jasschallenge.client.game.strategy.StrategySelector;

import java.util.EnumSet;
import java.util.Set;

public class Player {

    private final String name;
    private final Set<Card> cards;
    private final StrategySelector strategySelector;
    private Strategy currentStrategy;

    public Player(String name) {
        this(name, new StrategySelector(cards -> Mode.OBEABE, m -> new RandomMoveStrategy()));
    }

    public Player(String name, StrategySelector strategySelector) {
        this(name, strategySelector, EnumSet.noneOf(Card.class));
    }

    public Player(String name, StrategySelector strategySelector, Set<Card> cards) {
        this.name = name;
        this.cards = EnumSet.copyOf(cards);
        this.strategySelector = strategySelector;
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

    public void prepareForNewGame(Mode mode) {
        currentStrategy = strategySelector.createStrategyForMode(mode);
    }

    public Move makeMove(Round round) {
        if(cards.size() == 0) throw new RuntimeException("Cannot play a card without cards in deck");
        final Card cardToPlay = currentStrategy.calculateNextCardToPlay(round, cards);
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
