package com.zuehlke.jasschallenge.client.game;

import java.util.List;

import static com.zuehlke.jasschallenge.client.game.PlayingOrder.createOrder;
import static com.zuehlke.jasschallenge.client.game.PlayingOrder.createOrderStartingFromPlayer;

public class GameSession {

    private final PlayingOrder gameStartingPlayerOrder;
    private final List<Player> playersInPlayingOrder;
    private Round currentRound;

    public GameSession(List<Player> playersInPlayingOrder) {

        this.playersInPlayingOrder = playersInPlayingOrder;
        this.gameStartingPlayerOrder = createOrder(playersInPlayingOrder);
        startNewGame();
    }

    public Round getCurrentRound() {
        return currentRound;
    }

    public Round startNextRound() {

        final PlayingOrder nextPlayingOrder = createOrderStartingFromPlayer(playersInPlayingOrder, currentRound.getWinner());
        final int nextRoundNumber = currentRound.getRoundNumber() + 1;
        final Round nextRound  = Round.createRound(nextRoundNumber, nextPlayingOrder);
        this.currentRound = nextRound;
        return currentRound;
    }

    public Game startNewGame() {
        this.currentRound = Round.createRound(0, createOrderStartingFromPlayer(playersInPlayingOrder, gameStartingPlayerOrder.getCurrentPlayer()));
        gameStartingPlayerOrder.moveToNextPlayer();
        return new Game();
    }
}
