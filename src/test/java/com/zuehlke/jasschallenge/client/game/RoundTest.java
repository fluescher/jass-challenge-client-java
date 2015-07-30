package com.zuehlke.jasschallenge.client.game;

import com.zuehlke.jasschallenge.client.game.cards.Card;
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

        final Round round = Round.createRoundWithCardsPlayed(0, EnumSet.of(DIAMOND_ACE));

        assertThat(round.getRoundColor(), equalTo(DIAMONDS));
    }

    @Test
    public void getRoundColor_returnsNull_ifNoCardHasBeenPlayed() {

        final Round round = Round.createRoundWithCardsPlayed(0, EnumSet.noneOf(Card.class));

        assertNull(round.getRoundColor());
    }

    @Test
    public void getValue_returnsZero_ifNoCardsWerePlayed () {

        final Round round = Round.createRoundWithCardsPlayed(0, EnumSet.noneOf(Card.class));

        assertThat(round.getScore(), equalTo(0));
    }

    @Test
    public void getValue_returnsSumOfValues_ifSomeCardsWerePlayed () {

        final Round round = Round.createRoundWithCardsPlayed(0, EnumSet.of(DIAMOND_ACE, HEART_SIX, HEART_TEN));

        assertThat(round.getScore(), equalTo(21));
    }

    @Test(expected = RuntimeException.class)
    public void makeMove_throwsException_whenAlreadyEnoughCardsWerePlayed () {

        final Round round = Round.createRoundWithCardsPlayed(0, EnumSet.of(HEART_ACE, HEART_SIX, HEART_TEN, HEART_JACK));

        final Player player = new Player();
        round.makeMove(new Move(player, HEART_SEVEN));
    }

    @Test
    public void getWinner_returnsNull_ifNoCardWasPlayed() {
        final Round round = Round.createRound(0);

        final Player winner = round.getWinner();

        assertNull(winner);
    }

    @Test
    public void getWinner_returnsThePlayerWhichPlayedTheHighestCard() {
        final Player playerA = new Player();
        final Player playerB = new Player();
        final Player playerC = new Player();
        final Player playerD = new Player();

        final Round round = Round.createRound(0);
        round.makeMove(new Move(playerA, HEART_TEN));
        round.makeMove(new Move(playerB, HEART_QUEEN));
        round.makeMove(new Move(playerC, HEART_SEVEN));
        round.makeMove(new Move(playerD, HEART_JACK));

        assertThat(round.getWinner(), equalTo(playerB));
    }

    @Test
    public void getWinner_returnsThePlayerWhichPlayedTheHighestCard_withRoundColor() {
        final Player playerA = new Player();
        final Player playerB = new Player();
        final Player playerC = new Player();
        final Player playerD = new Player();

        final Round round = Round.createRound(0);
        round.makeMove(new Move(playerA, HEART_SIX));
        round.makeMove(new Move(playerB, DIAMOND_ACE));
        round.makeMove(new Move(playerC, HEART_SEVEN));
        round.makeMove(new Move(playerD, HEART_JACK));

        assertThat(round.getWinner(), equalTo(playerD));
    }
}