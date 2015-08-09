package com.zuehlke.jasschallenge.client.game.rules;

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
import static com.zuehlke.jasschallenge.client.game.cards.Color.CLUBS;
import static com.zuehlke.jasschallenge.client.game.cards.Color.HEARTS;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;
import static org.junit.Assume.assumeThat;

@RunWith(Theories.class)
public class TopDownRulesTest {

    @Test
    public void calculateScore_ifNoCardsWerePlayed_returnsZero() {

        int score = new TopDownRules().calculateScore(EnumSet.noneOf(Card.class));

        assertThat(score, equalTo(0));
    }

    @Test
    public void calculateScore_someCardsArePlayed_returnsSumOfCardScores() {

        final EnumSet<Card> cards = EnumSet.of(DIAMOND_ACE, HEART_SIX, HEART_TEN);

        final int score = new TopDownRules().calculateScore(cards);

        assertThat(score, equalTo(21));
    }

    @Theory
    public void canPlayCard_whenNoCardIsPlayed_everyCardIsAllowed(
            @ForAll Card cardToPlay) {

        final Set<Card> alreadyPlayedCards = EnumSet.noneOf(Card.class);
        final Set<Card> playerCards = EnumSet.of(cardToPlay);

        final boolean canCardBePlayed = new TopDownRules().canPlayCard(cardToPlay, alreadyPlayedCards, null, playerCards);

        assertTrue(canCardBePlayed);
    }

    @Theory
    public void canPlayCard_withPlayedCards_allowsCardsOfSameColor(
            @ForAll Card playedCard,
            @ForAll Card cardToPlay) {

        assumeThat(playedCard.getColor(), equalTo(cardToPlay.getColor()));

        final Set<Card> alreadyPlayedCards = EnumSet.of(playedCard);
        final Color roundColor = playedCard.getColor();
        final Set<Card> playerCards = EnumSet.of(cardToPlay);

        final boolean canCardBePlayed = new TopDownRules().canPlayCard(cardToPlay, alreadyPlayedCards, roundColor, playerCards);

        assertTrue(canCardBePlayed);
    }

    @Theory
    public void canPlayCard_withPlayedCards_allowsNoCardsOfOtherColor(
            @ForAll Card playedCard,
            @ForAll Card cardToPlay) {

        assumeThat(playedCard, not(equalTo(HEART_JACK)));
        assumeThat(playedCard.getColor(), equalTo(HEARTS));
        assumeThat(cardToPlay.getColor(), not(equalTo(HEARTS)));

        final Set<Card> alreadyPlayedCards = EnumSet.of(playedCard);
        final Color roundColor = playedCard.getColor();
        final Set<Card> playerCards = EnumSet.of(HEART_JACK);

        final boolean canCardBePlayed = new TopDownRules().canPlayCard(cardToPlay, alreadyPlayedCards, roundColor, playerCards);

        assertFalse(canCardBePlayed);
    }

    @Test
    public void canPlayCard_playerHasNoCardOfSameColorInDeck_allowsCardOfOtherColor() {

        final EnumSet<Card> alreadyPlayedCards = EnumSet.of(CLUB_SIX);
        final EnumSet<Card> playersCards = EnumSet.of(HEART_EIGHT, HEART_NINE);

        final boolean canCardBePlayed = new TopDownRules().canPlayCard(HEART_EIGHT, alreadyPlayedCards, CLUBS, playersCards);

        assertTrue(canCardBePlayed);
    }

    @Test
    public void determineWinner_noMovesWereMade_returnsNull() {

        final Player winner = new TopDownRules().determineWinner(emptyList(), null);

        assertNull(winner);
    }

    @Test
    public void determineWinner_movesWithAllTheSameColor_returnsThePlayerWhichPlayedTheHighestCard() {
        final Player playerA = new Player("a");
        final Player playerB = new Player("b");
        final Player playerC = new Player("c");
        final Player playerD = new Player("d");
        final List<Move> moves = asList(
                new Move(playerA, HEART_TEN),
                new Move(playerB, HEART_QUEEN),
                new Move(playerC, HEART_SEVEN),
                new Move(playerD, HEART_JACK));

        final Player winner = new TopDownRules().determineWinner(moves, HEARTS);

        assertThat(winner, equalTo(playerB));
    }

    @Test
    public void determineWinner_movesWithDifferentColors__returnsThePlayerWhichPlayedTheHighestCardOfRoundColor() {
        final Player playerA = new Player("a");
        final Player playerB = new Player("b");
        final Player playerC = new Player("c");
        final Player playerD = new Player("d");
        final List<Move> moves = asList(
                new Move(playerA, HEART_JACK),
                new Move(playerB, HEART_NINE),
                new Move(playerC, DIAMOND_EIGHT),
                new Move(playerD, DIAMOND_NINE));

        final Player winner = new TopDownRules().determineWinner(moves, Color.HEARTS);

        assertThat(winner, equalTo(playerA));
    }

}