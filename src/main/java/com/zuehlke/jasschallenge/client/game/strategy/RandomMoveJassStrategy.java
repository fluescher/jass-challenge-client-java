package com.zuehlke.jasschallenge.client.game.strategy;

import com.zuehlke.jasschallenge.client.game.Game;
import com.zuehlke.jasschallenge.client.game.GameSession;
import com.zuehlke.jasschallenge.client.game.Mode;
import com.zuehlke.jasschallenge.client.game.Round;
import com.zuehlke.jasschallenge.client.game.cards.Card;

import java.util.Set;

public class RandomMoveJassStrategy implements JassStrategy {
    @Override
    public Mode chooseTrumpf(Set<Card> availableCards, GameSession session) {
        return Mode.OBEABE;
    }

    @Override
    public Card chooseCard(Set<Card> availableCards, GameSession session) {
        final Game currentGame = session.getCurrentGame();
        final Round round = currentGame.getCurrentRound();

        return availableCards.stream()
                    .filter(card -> round.getRules().canPlayCard(card, round.getPlayedCards(), round.getRoundColor(), availableCards))
                    .findAny()
                    .orElse(availableCards.stream().findAny().get());
    }
}
