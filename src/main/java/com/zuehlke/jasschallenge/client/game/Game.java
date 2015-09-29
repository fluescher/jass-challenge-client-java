package com.zuehlke.jasschallenge.client.game;

import com.zuehlke.jasschallenge.client.game.mode.Mode;

import static com.zuehlke.jasschallenge.client.game.PlayingOrder.createOrderStartingFromPlayer;

public class Game {
    private final Mode mode;
    private final PlayingOrder order;
    private Round currentRound;

    public Game(Mode mode, PlayingOrder order) {
        this.mode = mode;
        this.order = order;

        this.currentRound = Round.createRound(mode, 0, order);
    }

    public static Game startGame(Mode mode, PlayingOrder order) {

        return new Game(mode, order);
    }

    public Round getCurrentRound() {
        return currentRound;
    }

    public Round startNextRound() {
        final PlayingOrder nextPlayingOrder = createOrderStartingFromPlayer(order.getPlayerInOrder(), currentRound.getWinner());
        final int nextRoundNumber = currentRound.getRoundNumber() + 1;
        final Round nextRound  = Round.createRound(mode, nextRoundNumber, nextPlayingOrder);
        this.currentRound = nextRound;
        return currentRound;
    }
}
