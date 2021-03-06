package com.zuehlke.jasschallenge.client.game;

import com.zuehlke.jasschallenge.game.cards.Card;
import com.zuehlke.jasschallenge.game.cards.Color;
import com.zuehlke.jasschallenge.game.mode.Mode;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class Round {
    private final Mode mode;
    private final int roundNumber;
    private final PlayingOrder playingOrder;
    private final List<Move> moves = new ArrayList<>();

    public static Round createRound(Mode gameMode, int roundNumber, PlayingOrder playingOrder) {
        return new Round(gameMode, roundNumber, playingOrder);
    }

    private Round(Mode mode, int roundNumber, PlayingOrder playingOrder) {
        this.mode = mode;
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

    public int calculateScore() {

        return mode.calculateRoundScore(roundNumber, getPlayedCards());
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
        final Move winningMove = mode.determineWinningMove(this.moves);
        if(winningMove == null) return null;

        return winningMove.getPlayer();
    }

    public List<Move> getMoves() {
        return moves;
    }

    public PlayingOrder getPlayingOrder() {
        return playingOrder;
    }

    public Mode getMode() {
        return mode;
    }

    public boolean isLastRound() {
        return getRoundNumber() == Game.LAST_ROUND_NUMBER;
    }
}
