package com.zuehlke.jasschallenge.client.game;

import com.zuehlke.jasschallenge.client.game.cards.Card;
import com.zuehlke.jasschallenge.client.game.cards.Color;

import java.util.*;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

public class Round {
    private final int roundNumber;
    private final List<Move> moves = new ArrayList<>();

    public static Round createRound(int roundNumber) {
        return new Round(roundNumber);
    }

    public static Round createRoundWithMoves(int roundNumber, List<Move> moves) {
        return new Round(roundNumber, moves);
    }

    public static Round createRoundWithCardsPlayed(int roundNumber, Set<Card> playedCards) {
        List<Move> moves = playedCards.stream().map(card -> new Move(new Player("unnamed"), card)).collect(toList());
        return createRoundWithMoves(roundNumber, moves);
    }

    private Round(int roundNumber) {
        this(roundNumber, emptyList());
    }

    private Round(int roundNumber, List<Move> moves) {
        this.roundNumber = roundNumber;
        this.moves.addAll(moves);
    }

    public void makeMove(Move move) {
        if(moves.size() == 4) {
            throw new RuntimeException("Only four card can be played in a round");
        }
        moves.add(move);
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
        if(moves.size() == 0) return null;

        return moves.get(0).getPlayedCard().getColor();
    }

    public int getScore() {
        return getPlayedCards().stream()
                          .mapToInt(Card::getScore)
                          .sum();
    }

    public Player getWinner() {
        return moves.stream()
                .filter(move -> move.getPlayedCard().getColor() == getRoundColor())
                .max((move, move2) -> move.getPlayedCard().isHigherThan(move2.getPlayedCard()) ? 1 : -1)
                .map(Move::getPlayer)
                .orElse(null);
    }

    public List<Move> getMoves() {
        return moves;
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
