package com.zuehlke.jasschallenge.client.game;

import com.zuehlke.jasschallenge.client.game.mode.Mode;

import java.util.List;

import static com.zuehlke.jasschallenge.client.game.PlayingOrder.createOrder;
import static com.zuehlke.jasschallenge.client.game.PlayingOrder.createOrderStartingFromPlayer;

public class GameSession {

    private final List<Team> teams;
    private final List<Player> playersInPlayingOrder;
    private final PlayingOrder gameStartingPlayerOrder;
    private Game currentGame;
    private Result result;

    public GameSession(List<Team> teams, List<Player> playersInPlayingOrder) {
        this.teams = teams;

        this.playersInPlayingOrder = playersInPlayingOrder;
        this.gameStartingPlayerOrder = createOrder(playersInPlayingOrder);

        result = new Result(teams.get(0), teams.get(0));
    }

    public Round getCurrentRound() {

        if(currentGame == null) return null;

        return currentGame.getCurrentRound();
    }

    public List<Team> getTeams() {
        return teams;
    }

    public Game startNewGame(Mode mode, boolean shifted) {

        updateResult();

        final PlayingOrder initialOrder = createOrderStartingFromPlayer(playersInPlayingOrder, gameStartingPlayerOrder.getCurrentPlayer());
        gameStartingPlayerOrder.moveToNextPlayer();

        currentGame = Game.startGame(mode, initialOrder, teams, shifted);
        return currentGame;
    }

    public Round startNextRound() {

        return currentGame.startNextRound();
    }

    public void makeMove(Move move) {

        currentGame.makeMove(move);
    }

    public Game getCurrentGame() {
        return currentGame;
    }

    public Result getResult() {
        return result;
    }

    private void updateResult() {
        if(currentGame == null) return;

        result.add(currentGame.getResult());
    }
}
