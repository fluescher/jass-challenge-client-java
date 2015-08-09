package com.zuehlke.jasschallenge.client.game;

import java.util.List;

import static java.util.Arrays.asList;

public class GameSessionBuilder {
    private List<Player> playersInPlayingOrder = asList(
            new Player("Player 1"),
            new Player("Player 2"),
            new Player("Player 3"),
            new Player("Player 4")
    );
    private List<Team> teams = asList(
            new Team("Team 1", asList(playersInPlayingOrder.get(0), playersInPlayingOrder.get(2))),
            new Team("Team 2", asList(playersInPlayingOrder.get(1), playersInPlayingOrder.get(3))));

    public GameSessionBuilder withTeams(List<Team> teams) {
        this.teams = teams;
        return this;
    }

    public GameSessionBuilder withPlayersInPlayingOrder(List<Player> playersInPlayingOrder) {
        this.playersInPlayingOrder = playersInPlayingOrder;
        return this;
    }

    public GameSession createGameSession() {
        return new GameSession(teams, playersInPlayingOrder);
    }
}