package com.zuehlke.jasschallenge.client.game;

import com.zuehlke.jasschallenge.client.game.cards.Card;
import com.zuehlke.jasschallenge.client.game.mode.Mode;
import com.zuehlke.jasschallenge.client.game.strategy.JassStrategy;
import com.zuehlke.jasschallenge.client.game.strategy.RandomJassStrategy;

import java.util.EnumSet;
import java.util.Set;

public class Player {

    private int id = -1;
    private final String name;
    private final Set<Card> cards;
    private final JassStrategy currentJassStrategy;

    public Player(int id, String name) {
        this(name);
        this.id = id;
    }

    public Player(String name) {
        this(name, new RandomJassStrategy());
    }

    public Player(String name, JassStrategy strategy) {
        this.name = name;
        this.cards = EnumSet.noneOf(Card.class);
        this.currentJassStrategy = strategy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        final Card cardToPlay = chooseCardWithFallback(session);
        cards.remove(cardToPlay);
        return new Move(this, cardToPlay);
    }

    private Card chooseCardWithFallback(GameSession session) {
        final Card cardToPlay = currentJassStrategy.chooseCard(cards, session);
        final boolean cardIsInvalid = !session.getCurrentRound().getMode().canPlayCard(
                cardToPlay,
                session.getCurrentRound().getPlayedCards(),
                session.getCurrentRound().getRoundColor(),
                cards);
        if(cardIsInvalid) {
            return new RandomJassStrategy().chooseCard(cards, session);
        }
        return cardToPlay;
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

        if (id != player.id) return false;
        return !(name != null ? !name.equals(player.name) : player.name != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cards=" + cards +
                ", currentJassStrategy=" + currentJassStrategy +
                '}';
    }
}
