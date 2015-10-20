package com.zuehlke.jasschallenge.localserver;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RoundTest {

    @Test
    public void allPlayersGet9Cards() {
        Player player1Team1 = new Player(null);
        Player player1Team2 = new Player(null);
        Player player2Team1 = new Player(null);
        Player player2Team2 = new Player(null);
        Round round = new Round(player1Team1, player1Team2, player2Team1, player2Team2);
        assertEquals(9, round.getCards(player1Team1).size());
        assertEquals(9, round.getCards(player1Team2).size());
        assertEquals(9, round.getCards(player2Team1).size());
        assertEquals(9, round.getCards(player2Team2).size());
    }

}