package com.zuehlke.jasschallenge.client.game;

import com.pholser.junit.quickcheck.ForAll;
import com.zuehlke.jasschallenge.client.game.cards.Card;
import org.junit.Test;
import org.junit.contrib.theories.Theories;
import org.junit.contrib.theories.Theory;
import org.junit.runner.RunWith;

import java.util.EnumSet;

import static com.zuehlke.jasschallenge.client.game.cards.Card.*;
import static com.zuehlke.jasschallenge.client.game.cards.Color.HEARTS;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeThat;

@RunWith(Theories.class)
public class PlayerTest {

    @Theory
    public void canPlayCard_allowsEveryCard_whenNoCardIsPlayed(
            @ForAll Card cardToPlay) {

        final Round round = Round.createRound(0);
        final Player player = new Player();

        final boolean canCardBePlayed = player.canPlayCard(cardToPlay, round);

        assertTrue(canCardBePlayed);
    }

    @Theory
    public void canPlayCard_allowsCardsOfSameColor(
            @ForAll Card playedCard,
            @ForAll Card cardToPlay) {

        assumeThat(playedCard.getColor(), equalTo(cardToPlay.getColor()));

        final Round round = Round.createRoundWithCardsPlayed(0, EnumSet.of(playedCard));
        final Player player = new Player();

        final boolean canCardBePlayed = player.canPlayCard(cardToPlay, round);

        assertTrue(canCardBePlayed);
    }

    @Theory
    public void canPlayCard_allowsNoCardsOfOtherColor(
            @ForAll Card playedCard,
            @ForAll Card cardToPlay) {

        assumeThat(playedCard, not(equalTo(HEART_JACK)));
        assumeThat(playedCard.getColor(), equalTo(HEARTS));
        assumeThat(cardToPlay.getColor(), not(equalTo(HEARTS)));

        final Round round = Round.createRoundWithCardsPlayed(0, EnumSet.of(playedCard));
        final Player player = new Player("unnamed", EnumSet.of(HEART_JACK));

        final boolean canCardBePlayed = player.canPlayCard(cardToPlay, round);

        assertFalse(canCardBePlayed);
    }

    @Test
    public void canPlayCard_allowsCardOfOtherColor_ifPlayerHasNoCardOfSameColorInDeck() {

        final Round round = Round.createRoundWithCardsPlayed(0, EnumSet.of(CLUB_SIX));
        final Player player = new Player("unnamed", EnumSet.of(HEART_EIGHT, HEART_NINE));

        final boolean canCardBePlayed = player.canPlayCard(HEART_EIGHT, round);

        assertTrue(canCardBePlayed);
    }

}