package com.zuehlke.jasschallenge.client.game;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class GameSessionTest {
    @Test
    public void newGameSession_withoutStartedGame() {

        final GameSession gameSession = new GameSessionBuilder().createGameSession();

        assertThat(gameSession.getCurrentRound(), is(nullValue()));
    }

    @Test
    public void startNewGame_whenNoGameWasStarted_firstRoundIsStarted() {

        final GameSession gameSession = new GameSessionBuilder().createGameSession();

        gameSession.startNewGame(Mode.OBEABE);

        assertThat(gameSession.getCurrentRound(), is(not(nullValue())));
    }

    @Test
    public void startNextRound_afterAPlayedRound_roundNumberIsIncreased() {

        final GameSession gameSession = new GameSessionBuilder().createGameSession();

        gameSession.startNewGame(Mode.OBEABE);
        Round secondRound = gameSession.startNextRound();

        assertThat(secondRound.getRoundNumber(), is(1));
    }
}