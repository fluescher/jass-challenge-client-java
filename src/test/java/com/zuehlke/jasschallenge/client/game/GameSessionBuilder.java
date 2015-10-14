package com.zuehlke.jasschallenge.client.game;

import com.zuehlke.jasschallenge.client.game.cards.Card;
import com.zuehlke.jasschallenge.client.game.mode.Mode;

import java.util.List;

import static java.util.Arrays.asList;

public class GameSessionBuilder {
    private Mode startedGameMode = null;
    private List<Player> playersInPlayingOrder = asList(
            new Player("Player 1"),
            new Player("Player 2"),
            new Player("Player 3"),
            new Player("Player 4")
    );
    private List<Team> teams = asList(
            new Team("Team 1", asList(playersInPlayingOrder.get(0), playersInPlayingOrder.get(2))),
            new Team("Team 2", asList(playersInPlayingOrder.get(1), playersInPlayingOrder.get(3))));

    private Card[] playedCards = {};

    public static GameSessionBuilder newSession() {
        return new GameSessionBuilder();
    }

    public GameSessionBuilder withTeams(List<Team> teams) {
        this.teams = teams;
        return this;
    }

    public GameSessionBuilder withPlayersInPlayingOrder(List<Player> playersInPlayingOrder) {
        this.playersInPlayingOrder = playersInPlayingOrder;
        return this;
    }

    public GameSession createGameSession() {
        final GameSession gameSession = new GameSession(teams, playersInPlayingOrder);
        if(startedGameMode != null) {
            gameSession.startNewGame(startedGameMode, false);
            for(Card card : playedCards) {
                gameSession.makeMove(new Move(gameSession.getCurrentRound().getPlayingOrder().getCurrentPlayer(), card));
                gameSession.getCurrentRound().getPlayingOrder().moveToNextPlayer();
            }
        }
        return gameSession;
    }

    public GameSessionBuilder withStartedGame(Mode mode) {
        startedGameMode = mode;
        return this;
    }

    public GameSessionBuilder withCardsPlayed(Card... cards) {
        playedCards = cards;
        return this;
    }
}