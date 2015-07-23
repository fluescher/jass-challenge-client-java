package com.zuehlke.jasschallenge.client.game;

import com.zuehlke.jasschallenge.client.game.cards.Card;
import org.junit.Test;

import static com.zuehlke.jasschallenge.client.game.cards.CardValue.ACE;
import static com.zuehlke.jasschallenge.client.game.cards.Color.DIAMONDS;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class RoundTest {

    @Test
    public void getRoundColor_returnsColorOfFirstCard() {

        final Round round = new Round(0, asList(new Card(ACE, DIAMONDS)));

        assertEquals(DIAMONDS, round.getRoundColor());
    }

    @Test
    public void getRoundColor_returnsNull_ifNoCardHasBeenPlayed() {

        final Round round = new Round(0, asList());

        assertNull(round.getRoundColor());
    }

}