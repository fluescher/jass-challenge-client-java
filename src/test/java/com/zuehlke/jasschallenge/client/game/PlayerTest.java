package com.zuehlke.jasschallenge.client.game;

import com.pholser.junit.quickcheck.ForAll;
import com.pholser.junit.quickcheck.From;
import com.zuehlke.jasschallenge.client.game.cards.Card;
import com.zuehlke.jasschallenge.client.game.cards.CardGenerator;
import com.zuehlke.jasschallenge.client.game.cards.Color;
import com.zuehlke.jasschallenge.client.game.cards.ColorGenerator;
import org.junit.contrib.theories.Theories;
import org.junit.contrib.theories.Theory;
import org.junit.runner.RunWith;

import static com.zuehlke.jasschallenge.client.game.cards.CardValue.KING;
import static com.zuehlke.jasschallenge.client.game.cards.Color.HEARTS;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;
import static org.junit.Assume.assumeThat;

@RunWith(Theories.class)
public class PlayerTest {

    @Theory
    public void canPlayCard_allowsEveryCard_whenNoCardIsPlayed(
            @ForAll @From(CardGenerator.class) Card card) {

        final Round round = new Round(0);
        final Player player = new Player();

        final boolean canCardBePlayed = player.canPlayCard(card, round);

        assertTrue(canCardBePlayed);
    }

    @Theory
    public void canPlayCard_allowsCardsOfSameColor(
            @ForAll @From(CardGenerator.class) Card card,
            @ForAll @From(ColorGenerator.class) Color color) {

        assumeThat(card.getColor(), equalTo(color));

        final Round round = new Round(0, singletonList(new Card(KING, color)));
        final Player player = new Player();

        final boolean canCardBePlayed = player.canPlayCard(card, round);

        assertTrue(canCardBePlayed);
    }

    @Theory
    public void canPlayCard_allowsNoCardsOfOtherColor(
            @ForAll @From(CardGenerator.class) Card card) {

        assumeThat(card.getColor(), not(equalTo(HEARTS)));

        final Round round = new Round(0, singletonList(new Card(KING, HEARTS)));
        final Player player = new Player();

        final boolean canCardBePlayed = player.canPlayCard(card, round);

        assertFalse(canCardBePlayed);
    }

}