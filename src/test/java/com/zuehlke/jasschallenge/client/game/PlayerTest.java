package com.zuehlke.jasschallenge.client.game;

import com.pholser.junit.quickcheck.ForAll;
import com.pholser.junit.quickcheck.From;
import com.zuehlke.jasschallenge.client.game.cards.Card;
import com.zuehlke.jasschallenge.client.game.cards.CardGenerator;
import org.junit.contrib.theories.Theories;
import org.junit.contrib.theories.Theory;
import org.junit.runner.RunWith;

import java.util.EnumSet;

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
            @ForAll @From(CardGenerator.class) Card card) {

        final Round round = Round.createRound(0);
        final Player player = new Player();

        final boolean canCardBePlayed = player.canPlayCard(card, round);

        assertTrue(canCardBePlayed);
    }

    @Theory
    public void canPlayCard_allowsCardsOfSameColor(
            @From(CardGenerator.class) Card card,
            @From(CardGenerator.class) Card cardToPlay) {

        assumeThat(card.getColor(), equalTo(cardToPlay.getColor()));

        final Round round = Round.createRoundWithCardsPlayed(0, EnumSet.of(card));
        final Player player = new Player();

        final boolean canCardBePlayed = player.canPlayCard(cardToPlay, round);

        assertTrue(canCardBePlayed);
    }

    @Theory
    public void canPlayCard_allowsNoCardsOfOtherColor(
            @From(CardGenerator.class) Card card,
            @From(CardGenerator.class) Card cardToPlay) {

        assumeThat(card.getColor(), equalTo(HEARTS));
        assumeThat(cardToPlay.getColor(), not(equalTo(HEARTS)));

        final Round round = Round.createRoundWithCardsPlayed(0, EnumSet.of(card));
        final Player player = new Player();

        final boolean canCardBePlayed = player.canPlayCard(cardToPlay, round);

        assertFalse(canCardBePlayed);
    }

}