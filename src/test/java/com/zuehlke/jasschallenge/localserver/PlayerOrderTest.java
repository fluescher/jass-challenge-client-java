package com.zuehlke.jasschallenge.localserver;

import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerOrderTest {

    @Test
    public void testGetNextToPlay() throws Exception {
        Player player1Team1 = new Player(null);
        Player player1Team2 = new Player(null);
        Player player2Team1 = new Player(null);
        Player player2Team2 = new Player(null);

        PlayerOrder order = new PlayerOrder(player1Team1, player1Team2, player2Team1, player2Team2);

        assertEquals(player1Team1, order.getNextToPlay());
        assertEquals(player1Team2, order.getNextToPlay());
        assertEquals(player2Team1, order.getNextToPlay());
        assertEquals(player2Team2, order.getNextToPlay());
    }


    @Test
    public  void testNewRound() {
        Player player1Team1 = new Player(null);
        Player player1Team2 = new Player(null);
        Player player2Team1 = new Player(null);
        Player player2Team2 = new Player(null);

        PlayerOrder order = new PlayerOrder(player1Team1, player1Team2, player2Team1, player2Team2);

        order.newRound();

        assertEquals(player1Team2, order.getNextToPlay());
        assertEquals(player2Team1, order.getNextToPlay());
        assertEquals(player2Team2, order.getNextToPlay());
        assertEquals(player1Team1, order.getNextToPlay());

    }

    @Test
    public  void testStichMade() {
        Player player1Team1 = new Player(null);
        Player player1Team2 = new Player(null);
        Player player2Team1 = new Player(null);
        Player player2Team2 = new Player(null);

        PlayerOrder order = new PlayerOrder(player1Team1, player1Team2, player2Team1, player2Team2);

        order.stichMade(player2Team1);

        assertEquals(player2Team1, order.getNextToPlay());
        assertEquals(player2Team2, order.getNextToPlay());
        assertEquals(player1Team1, order.getNextToPlay());
        assertEquals(player1Team2, order.getNextToPlay());

    }
}