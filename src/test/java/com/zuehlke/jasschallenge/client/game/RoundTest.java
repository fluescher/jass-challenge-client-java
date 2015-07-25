package com.zuehlke.jasschallenge.client.game;

import com.zuehlke.jasschallenge.client.game.cards.Card;
import org.junit.Test;

import java.util.EnumSet;

import static com.zuehlke.jasschallenge.client.game.cards.Color.DIAMONDS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class RoundTest {

    @Test
    public void getRoundColor_returnsColorOfFirstCard() {

        final Round round = new Round(0, EnumSet.of(Card.DIAMOND_ACE));

        assertEquals(DIAMONDS, round.getRoundColor());
    }

    @Test
    public void getRoundColor_returnsNull_ifNoCardHasBeenPlayed() {

        final Round round = new Round(0, EnumSet.noneOf(Card.class));

        assertNull(round.getRoundColor());
    }

}