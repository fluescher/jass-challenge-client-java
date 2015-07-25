package com.zuehlke.jasschallenge.client.game;

import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class GameTest {

    @Test
    public void test() {
        Game game = new Game(Collections.<Player>emptyList());
        Round round = game.startRound();

    }

    @Test
    public void startRound_startsWithFirstRound() {
        Game game = new Game(Collections.<Player>emptyList());

        Round round = game.startRound();

        assertEquals(0, round.getRoundNumber());
    }

}