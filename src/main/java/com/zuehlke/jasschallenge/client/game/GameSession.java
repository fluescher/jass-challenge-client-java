package com.zuehlke.jasschallenge.client.game;

import java.util.List;

import static com.zuehlke.jasschallenge.client.game.PlayingOrder.createOrder;
import static com.zuehlke.jasschallenge.client.game.PlayingOrder.createOrderStartingFromPlayer;

public class GameSession {

    private final List<Team> teams;
    private final List<Player> playersInPlayingOrder;
    private final PlayingOrder gameStartingPlayerOrder;
    private Game currentGame;

    public GameSession(List<Team> teams, List<Player> playersInPlayingOrder) {
        this.teams = teams;

        this.playersInPlayingOrder = playersInPlayingOrder;
        this.gameStartingPlayerOrder = createOrder(playersInPlayingOrder);
    }

    public Round getCurrentRound() {

        if(currentGame == null) return null;

        return currentGame.getCurrentRound();
    }

    public List<Team> getTeams() {
        return teams;
    }

    public Game startNewGame(Mode mode) {

        final PlayingOrder initialOrder = createOrderStartingFromPlayer(playersInPlayingOrder, gameStartingPlayerOrder.getCurrentPlayer());
        gameStartingPlayerOrder.moveToNextPlayer();

        currentGame = Game.startGame(mode, initialOrder);
        return currentGame;
    }

    public Round startNextRound() {

        return currentGame.startNextRound();
    }

    public void makeMove(Move move) {

        currentGame.getCurrentRound().makeMove(move);
    }

    public Game getCurrentGame() {
        return currentGame;
    }
}
