package com.zuehlke.jasschallenge.client.game.mode;

import com.pholser.junit.quickcheck.ForAll;
import com.zuehlke.jasschallenge.client.game.Move;
import com.zuehlke.jasschallenge.client.game.Player;
import com.zuehlke.jasschallenge.client.game.cards.Card;
import com.zuehlke.jasschallenge.client.game.cards.Color;
import org.junit.Test;
import org.junit.contrib.theories.Theories;
import org.junit.contrib.theories.Theory;
import org.junit.runner.RunWith;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import static com.zuehlke.jasschallenge.client.game.cards.Card.*;
import static com.zuehlke.jasschallenge.client.game.cards.Color.*;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;
import static org.junit.Assume.assumeThat;
import static org.junit.Assume.assumeTrue;

@RunWith(Theories.class)
public class TrumpfColorModeTest {

    @Test
    public void calculateScore_noCardHasBeenPlayedYet() {

        int result = Mode.trump(CLUBS).calculateScore(EnumSet.noneOf(Card.class));

        assertEquals(0, result);
    }

    @Test
    public void calculateScore_someNoTrumpfCardsWerePlayed() {

        final EnumSet<Card> playedCards = EnumSet.of(Card.DIAMOND_ACE, Card.DIAMOND_KING, Card.HEART_TEN);

        final int score = Mode.trump(CLUBS).calculateScore(playedCards);

        assertThat(score, equalTo(25));
    }

    @Test
    public void calculateScore_trumpfCardsHaveSpecialScores() {

        final EnumSet<Card> playedCards = EnumSet.of(Card.CLUB_JACK, Card.CLUB_NINE);

        final int score = Mode.trump(CLUBS).calculateScore(playedCards);

        assertThat(score, equalTo(34));
    }

    @Test
    public void determineWinner_noMovesWereMade_returnsNull() {

        final Player winner = Mode.trump(CLUBS).determineWinner(emptyList());

        assertNull(winner);
    }

    @Test
    public void canPlayCard_whenTrumpfIsRoundColor_underTrumpfAllowed() {

        final Set<Card> playedCards = EnumSet.of(SPADE_ACE);
        final Set<Card> playerCards = EnumSet.of(
                SPADE_TEN,SPADE_EIGHT,SPADE_SEVEN,
                DIAMOND_QUEEN
        );

        final boolean canPlayCard = Mode.trump(SPADES).canPlayCard(SPADE_TEN, playedCards, SPADES, playerCards);

        assertTrue(canPlayCard);
    }

    @Test
    public void determineWinner_noTrumpfHasBeenPlayed() {

        final Player playerA = new Player("a");
        final Player playerB = new Player("b");
        final Player playerC = new Player("c");
        final Player playerD = new Player("d");
        final List<Move> moves = asList(
                new Move(playerA, HEART_TEN),
                new Move(playerB, HEART_QUEEN),
                new Move(playerC, HEART_SEVEN),
                new Move(playerD, HEART_JACK));

        Player winner = Mode.trump(CLUBS).determineWinner(moves);

        assertThat(winner, equalTo(playerB));
    }

    @Test
    public void determineWinner_onlyTrumpfHasBeenPlayed() {
        final Player playerA = new Player("a");
        final Player playerB = new Player("b");
        final Player playerC = new Player("c");
        final Player playerD = new Player("d");
        final List<Move> moves = asList(
                new Move(playerA, HEART_TEN),
                new Move(playerB, HEART_QUEEN),
                new Move(playerC, HEART_SEVEN),
                new Move(playerD, HEART_JACK));

        Player winner = Mode.trump(HEARTS).determineWinner(moves);

        assertThat(winner, equalTo(playerD));
    }

    @Test
    public void determineWinner_trumpfAndRegularColorsHaveBeenPlayed() {
        final Player playerA = new Player("a");
        final Player playerB = new Player("b");
        final Player playerC = new Player("c");
        final Player playerD = new Player("d");
        final List<Move> moves = asList(
                new Move(playerA, CLUB_TEN),
                new Move(playerB, CLUB_EIGHT),
                new Move(playerC, CLUB_ACE),
                new Move(playerD, HEART_SIX));

        Player winner = Mode.trump(HEARTS).determineWinner(moves);

        assertThat(winner, equalTo(playerD));
    }

    @Test
    public void determineWinner_trumpfJackBeatsAce() {
        final Player playerA = new Player("a");
        final Player playerB = new Player("b");
        final Player playerC = new Player("c");
        final Player playerD = new Player("d");
        final List<Move> moves = asList(
                new Move(playerA, CLUB_TEN),
                new Move(playerB, HEART_NINE),
                new Move(playerC, CLUB_ACE),
                new Move(playerD, HEART_ACE));

        Player winner = Mode.trump(HEARTS).determineWinner(moves);

        assertThat(winner, equalTo(playerB));
    }

    @Theory
    public void canPlayCard_whenNoCardIsPlayed_everyCardIsAllowed(
            @ForAll Card cardToPlay) {

        final Set<Card> alreadyPlayedCards = EnumSet.noneOf(Card.class);
        final Set<Card> playerCards = EnumSet.of(cardToPlay);

        final boolean canCardBePlayed = Mode.trump(HEARTS).canPlayCard(cardToPlay, alreadyPlayedCards, null, playerCards);

        assertTrue(canCardBePlayed);
    }

    @Theory
    public void canPlayCard_withPlayedCards_allowsCardsOfSameColor(
            @ForAll Card playedCard,
            @ForAll Card cardToPlay) {

        assumeThat(playedCard, not(equalTo(cardToPlay)));
        assumeThat(playedCard.getColor(), equalTo(cardToPlay.getColor()));

        final Set<Card> alreadyPlayedCards = EnumSet.of(playedCard);
        final Color roundColor = playedCard.getColor();
        final Set<Card> playerCards = EnumSet.of(cardToPlay);

        final boolean canCardBePlayed = Mode.trump(HEARTS).canPlayCard(cardToPlay, alreadyPlayedCards, roundColor, playerCards);

        assertTrue(canCardBePlayed);
    }

    @Test
    public void canPlayCard_withOnlyTrumpfInHand_underTrumpfIsAllowed() {

        final Set<Card> alreadyPlayedCards = EnumSet.of(DIAMOND_ACE, HEART_NINE);
        final Set<Card> playerCards = EnumSet.of(HEART_ACE, HEART_SEVEN);

        final boolean canCardBePlayed = Mode.trump(HEARTS).canPlayCard(HEART_SEVEN, alreadyPlayedCards, Color.DIAMONDS, playerCards);

        assertTrue(canCardBePlayed);
    }

    @Test
    public void canPlayCard_withNoCardsOfRoundColor_undertrumpfStillNotAllowed() {

        final Set<Card> alreadyPlayedCards = EnumSet.of(DIAMOND_SIX, HEART_SEVEN, HEART_EIGHT);
        final Set<Card> playerCards = EnumSet.of(HEART_QUEEN, HEART_SIX, HEART_KING, CLUB_SEVEN, CLUB_KING, SPADE_SIX, SPADE_TEN, SPADE_JACK, SPADE_KING);

        final boolean canCardBePlayed = Mode.trump(HEARTS).canPlayCard(HEART_SIX, alreadyPlayedCards, Color.DIAMONDS, playerCards);

        assertFalse(canCardBePlayed);
    }

    @Test
    public void canPlayCard_withPlayedCards_underTrumpfIsNotAllowed() {

        final Set<Card> alreadyPlayedCards = EnumSet.of(DIAMOND_SIX, HEART_SIX, HEART_EIGHT);
        final Set<Card> playerCards = EnumSet.of(HEART_ACE, DIAMOND_JACK, HEART_SEVEN);

        final boolean canCardBePlayed = Mode.trump(HEARTS).canPlayCard(HEART_SEVEN, alreadyPlayedCards, Color.DIAMONDS, playerCards);

        assertFalse(canCardBePlayed);
    }

    @Test
    public void canPlayCard_withOnlyJackOfTrumpfColor() {

        final Set<Card> alreadyPlayedCards = EnumSet.of(CLUB_SEVEN);
        final Set<Card> playerCards = EnumSet.of(CLUB_JACK, HEART_SEVEN);

        final boolean canCardBePlayed = Mode.trump(CLUBS).canPlayCard(HEART_SEVEN, alreadyPlayedCards, CLUBS, playerCards);

        assertTrue(canCardBePlayed);
    }

    @Theory
    public void canPlayCard_withPlayedCards_allowsNoCardsOfOtherColor(
            @ForAll Card playedCard,
            @ForAll Card cardToPlay) {

        assumeThat(playedCard.getColor(), equalTo(HEARTS));
        assumeThat(playedCard, not(equalTo(cardToPlay)));
        assumeThat(cardToPlay.getColor(), not(equalTo(HEARTS)));
        assumeThat(cardToPlay.getColor(), not(equalTo(CLUBS)));

        final Set<Card> alreadyPlayedCards = EnumSet.of(playedCard);
        final Set<Card> playerCards = EnumSet.of(HEART_ACE, cardToPlay);

        final boolean canCardBePlayed = Mode.trump(CLUBS).canPlayCard(cardToPlay, alreadyPlayedCards, playedCard.getColor(), playerCards);

        assertFalse(canCardBePlayed);
    }

    @Theory
    public void canPlayCard_noTrumpfWasPlayed_trumpfCanAlwaysBePlayed(
            @ForAll Card playedCard,
            @ForAll Card cardToPlay) {

        assumeThat(cardToPlay.getColor(), equalTo(CLUBS));
        assumeTrue(cardToPlay.isHigherTrumpfThan(playedCard));

        final Set<Card> alreadyPlayedCards = EnumSet.of(playedCard);
        final Set<Card> playerCards = EnumSet.of(cardToPlay);

        final boolean canCardBePlayed = Mode.trump(CLUBS).canPlayCard(cardToPlay, alreadyPlayedCards, playedCard.getColor(), playerCards);

        assertTrue(canCardBePlayed);
    }
}