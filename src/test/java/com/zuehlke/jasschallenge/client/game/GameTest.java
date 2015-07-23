package com.zuehlke.jasschallenge.client.game;

import org.junit.Test;

import static org.junit.Assert.*;

public class GameTest {

    @Test
    public void test() {
        Game game = new Game();

        Round round = game.startRound();

        assertEquals(0, round.getRoundNumber());
    }

}