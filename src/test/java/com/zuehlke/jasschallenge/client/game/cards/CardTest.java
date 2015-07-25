package com.zuehlke.jasschallenge.client.game.cards;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CardTest {

    @Test
    public void allCards_containsEveryCard() {
        assertEquals(36, Card.values().length);
    }

}
