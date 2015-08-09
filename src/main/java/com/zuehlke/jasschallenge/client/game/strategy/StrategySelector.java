package com.zuehlke.jasschallenge.client.game.strategy;

import com.zuehlke.jasschallenge.client.game.Mode;
import com.zuehlke.jasschallenge.client.game.cards.Card;

import java.util.Set;
import java.util.function.Function;

public class StrategySelector {

    private final Function<Set<Card>,Mode> selectMode;
    private final Function<Mode,Strategy> createStrategy;

    public StrategySelector(Function<Set<Card>, Mode> selectMode, Function<Mode, Strategy> createStrategy) {
        this.selectMode = selectMode;
        this.createStrategy = createStrategy;
    }

    public Mode selectModeBasedOnCards(Set<Card> cards) {
        return selectMode.apply(cards);
    }

    public Strategy createStrategyForMode(Mode mode) {
        return createStrategy.apply(mode);
    }
}
