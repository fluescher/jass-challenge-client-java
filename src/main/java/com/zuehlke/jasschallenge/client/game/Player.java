package com.zuehlke.jasschallenge.client.game;

import com.zuehlke.jasschallenge.client.game.cards.Card;
import com.zuehlke.jasschallenge.client.game.mode.Mode;
import com.zuehlke.jasschallenge.client.game.strategy.JassStrategy;
import com.zuehlke.jasschallenge.client.game.strategy.RandomMoveJassStrategy;

import java.util.EnumSet;
import java.util.Set;

public class Player {

    private final String name;
    private final Set<Card> cards;
    private final JassStrategy currentJassStrategy;

    public Player(String name) {
        this(name, new RandomMoveJassStrategy());
    }

    public Player(String name, JassStrategy strategy) {
        this(name, strategy, EnumSet.noneOf(Card.class));
    }

    public Player(String name, JassStrategy strategySelector, Set<Card> cards) {
        this.name = name;
        this.cards = EnumSet.copyOf(cards);
        this.currentJassStrategy = strategySelector;
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

    public Move makeMove(GameSession session) {
        if(cards.size() == 0) throw new RuntimeException("Cannot play a card without cards in deck");
        final Card cardToPlay = currentJassStrategy.chooseCard(cards, session);
        cards.remove(cardToPlay);
        return new Move(this, cardToPlay);
    }

    public Card chooseCard(GameSession session) {
        return currentJassStrategy.chooseCard(cards, session);
    }

    public Mode chooseTrumpf(GameSession session) {
        return currentJassStrategy.chooseTrumpf(cards, session);
    }

    public void onMoveMade(Move move, GameSession session) {
        currentJassStrategy.onMoveMade(move, session);
    }

    public void onSessionFinished() {
        currentJassStrategy.onSessionFinished();
    }

    public void onGameFinished() {
        currentJassStrategy.onGameFinished();
    }

    public void onGameStarted(GameSession session) {
        currentJassStrategy.onGameStarted(session);
    }

    public void onSessionStarted(GameSession session) {
        currentJassStrategy.onSessionStarted(session);
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

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                '}';
    }
}
