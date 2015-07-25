package com.zuehlke.jasschallenge.client.game;

import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class GameTest {

    @Test
    public void startRound_startsWithFirstRound() {
        Game game = new Game();

        Round round = game.startRound();

        assertEquals(0, round.getRoundNumber());
    }

}