package com.zuehlke.jasschallenge.client.game.strategy;

import com.zuehlke.jasschallenge.client.game.Game;
import com.zuehlke.jasschallenge.client.game.GameSession;
import com.zuehlke.jasschallenge.client.game.Round;
import com.zuehlke.jasschallenge.client.game.cards.Card;
import com.zuehlke.jasschallenge.client.game.cards.Color;
import com.zuehlke.jasschallenge.client.game.mode.Mode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class RandomJassStrategy implements JassStrategy {
    @Override
    public Mode chooseTrumpf(Set<Card> availableCards, GameSession session) {
        final List<Mode> allPossibleModes = new ArrayList<>();
        allPossibleModes.add(Mode.bottomUp());
        allPossibleModes.add(Mode.topDown());
        for(Color color : Color.values()) {
            allPossibleModes.add(Mode.trump(color));
        }
        return allPossibleModes.get(new Random().nextInt(allPossibleModes.size()));
    }

    @Override
    public Card chooseCard(Set<Card> availableCards, GameSession session) {
        final Game currentGame = session.getCurrentGame();
        final Round round = currentGame.getCurrentRound();
        final Mode gameMode = round.getMode();

        return availableCards.stream()
                    .filter(card -> gameMode.canPlayCard(card, round.getPlayedCards(), round.getRoundColor(), availableCards))
                    .findAny()
                    .orElseThrow(() -> new RuntimeException("There should always be a card to play"));
    }
}
