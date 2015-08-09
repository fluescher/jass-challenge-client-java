package com.zuehlke.jasschallenge.client.game;

import com.pholser.junit.quickcheck.ForAll;
import com.zuehlke.jasschallenge.client.game.cards.Card;
import org.junit.Test;
import org.junit.contrib.theories.Theories;
import org.junit.contrib.theories.Theory;
import org.junit.runner.RunWith;

import java.util.EnumSet;
import java.util.List;

import static com.zuehlke.jasschallenge.client.game.cards.Card.*;
import static com.zuehlke.jasschallenge.client.game.cards.Color.HEARTS;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
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

        final Round round = Round.createRound(0, null);
        final Player player = new Player();

        final boolean canCardBePlayed = player.canPlayCard(cardToPlay, round);

        assertTrue(canCardBePlayed);
    }

    @Theory
    public void canPlayCard_allowsCardsOfSameColor(
            @ForAll Card playedCard,
            @ForAll Card cardToPlay) {

        assumeThat(playedCard.getColor(), equalTo(cardToPlay.getColor()));

        final Round round = Round.createRoundWithMoves(0, null, asList(new Move(new Player("unnamed"), playedCard)));
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

        final Round round = Round.createRoundWithMoves(0, null, asList(new Move(new Player("unnamed"), playedCard)));
        final Player player = new Player("unnamed", EnumSet.of(HEART_JACK));

        final boolean canCardBePlayed = player.canPlayCard(cardToPlay, round);

        assertFalse(canCardBePlayed);
    }

    @Test
    public void canPlayCard_allowsCardOfOtherColor_ifPlayerHasNoCardOfSameColorInDeck() {

        final Player previous = new Player("another");
        final Player player = new Player("unnamed", EnumSet.of(HEART_EIGHT, HEART_NINE));
        final Round round = Round.createRoundWithMoves(0, null, asList(new Move(previous, CLUB_SIX)));

        final boolean canCardBePlayed = player.canPlayCard(HEART_EIGHT, round);

        assertTrue(canCardBePlayed);
    }

}