package com.zuehlke.jasschallenge.client.game;

import com.zuehlke.jasschallenge.client.game.rules.TopDownRules;

import java.util.List;

import static com.zuehlke.jasschallenge.client.game.PlayingOrder.createOrder;
import static com.zuehlke.jasschallenge.client.game.PlayingOrder.createOrderStartingFromPlayer;

public class GameSession {

    private final List<Team> teams;
    private final List<Player> playersInPlayingOrder;
    private final PlayingOrder gameStartingPlayerOrder;
    private Round currentRound;

    public GameSession(List<Team> teams, List<Player> playersInPlayingOrder) {
        this.teams = teams;

        this.playersInPlayingOrder = playersInPlayingOrder;
        this.gameStartingPlayerOrder = createOrder(playersInPlayingOrder);
    }

    public Round getCurrentRound() {
        return currentRound;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public Game startNewGame(Mode obeabe) {

        final PlayingOrder initialOrder = createOrderStartingFromPlayer(playersInPlayingOrder, gameStartingPlayerOrder.getCurrentPlayer());
        this.currentRound = Round.createRound(new TopDownRules(), 0, initialOrder);
        gameStartingPlayerOrder.moveToNextPlayer();
        return new Game();
    }

    public Round startNextRound() {

        final PlayingOrder nextPlayingOrder = createOrderStartingFromPlayer(playersInPlayingOrder, currentRound.getWinner());
        final int nextRoundNumber = currentRound.getRoundNumber() + 1;
        final Round nextRound  = Round.createRound(currentRound.getRules(), nextRoundNumber, nextPlayingOrder);
        this.currentRound = nextRound;
        return currentRound;
    }
}
