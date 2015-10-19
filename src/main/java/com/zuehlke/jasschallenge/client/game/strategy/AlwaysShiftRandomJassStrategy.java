package com.zuehlke.jasschallenge.client.game.strategy;

import com.zuehlke.jasschallenge.client.game.GameSession;
import com.zuehlke.jasschallenge.game.cards.Card;
import com.zuehlke.jasschallenge.game.mode.Mode;

import java.util.*;

public class AlwaysShiftRandomJassStrategy extends RandomJassStrategy {

    @Override
    public Mode chooseTrumpf(Set<Card> availableCards, GameSession session, boolean isGschobe) {
        if (isGschobe) {
            List<Mode> allPossibleModes = Mode.standardModes();
            return allPossibleModes.get(new Random().nextInt(allPossibleModes.size()));
        }
        return Mode.shift();
    }
}
