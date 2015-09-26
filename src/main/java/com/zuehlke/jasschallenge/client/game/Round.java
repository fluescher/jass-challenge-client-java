package com.zuehlke.jasschallenge.client.game;

import com.zuehlke.jasschallenge.client.game.cards.Card;
import com.zuehlke.jasschallenge.client.game.cards.Color;
import com.zuehlke.jasschallenge.client.game.rules.Rules;

import java.util.*;

import static java.util.stream.Collectors.toSet;

public class Round {
    private final Rules rules;
    private final int roundNumber;
    private final PlayingOrder playingOrder;
    private final List<Move> moves = new ArrayList<>();

    public static Round createRound(Mode gameMode, int roundNumber, PlayingOrder playingOrder) {
        return new Round(gameMode.getRules(), roundNumber, playingOrder);
    }

    private Round(Rules rules, int roundNumber, PlayingOrder playingOrder) {
        this.rules = rules;
        this.roundNumber = roundNumber;
        this.playingOrder = playingOrder;
    }

    public void makeMove(Move move) {
        if (!move.getPlayer().equals(playingOrder.getCurrentPlayer()))
            throw new RuntimeException("It's not players "+move.getPlayer()+" turn");
        if (moves.size() == 4)
            throw new RuntimeException("Only four card can be played in a round");

        moves.add(move);
        playingOrder.moveToNextPlayer();
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public Set<Card> getPlayedCards() {
        return moves.stream()
                .map(Move::getPlayedCard)
                .collect(toSet());
    }

    public Color getRoundColor() {
        if (moves.size() == 0) return null;

        return moves.get(0).getPlayedCard().getColor();
    }

    public Player getWinner() {
        return rules.determineWinner(this.moves);
    }

    public List<Move> getMoves() {
        return moves;
    }

    public PlayingOrder getPlayingOrder() {
        return playingOrder;
    }

    public Rules getRules() {
        return rules;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Round round = (Round) o;

        if (roundNumber != round.roundNumber) return false;
        return !(moves != null ? !moves.equals(round.moves) : round.moves != null);

    }

    @Override
    public int hashCode() {
        int result = roundNumber;
        result = 31 * result + (moves != null ? moves.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Round{" +
                "roundNumber=" + roundNumber +
                ", moves=" + moves +
                '}';
    }


}
