package com.zuehlke.jasschallenge.client.game;

import com.zuehlke.jasschallenge.client.game.cards.Card;
import com.zuehlke.jasschallenge.client.game.rules.TopDownRules;
import org.junit.Test;

import java.util.EnumSet;

import static com.zuehlke.jasschallenge.client.game.cards.Card.*;
import static com.zuehlke.jasschallenge.client.game.cards.Color.DIAMONDS;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class RoundTest {

    @Test
    public void getRoundColor_returnsColorOfFirstCard() {

        final Round round = Round.createRoundWithCardsPlayed(new TopDownRules(), 0, null, EnumSet.of(DIAMOND_ACE));

        assertThat(round.getRoundColor(), equalTo(DIAMONDS));
    }

    @Test
    public void getRoundColor_noCardHasBeenPlayed_returnsNull() {

        final Round round = Round.createRoundWithCardsPlayed(new TopDownRules(), 0, null, EnumSet.noneOf(Card.class));

        assertNull(round.getRoundColor());
    }

    @Test(expected = RuntimeException.class)
    public void makeMove_throwsException_whenAlreadyEnoughCardsWerePlayed () {

        final Round round = Round.createRoundWithCardsPlayed(new TopDownRules(), 0, null, EnumSet.of(HEART_ACE, HEART_SIX, HEART_TEN, HEART_JACK));

        final Player player = new Player();
        round.makeMove(new Move(player, HEART_SEVEN));
    }

}