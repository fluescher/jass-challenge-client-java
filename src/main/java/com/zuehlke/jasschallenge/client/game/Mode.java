package com.zuehlke.jasschallenge.client.game;

import com.zuehlke.jasschallenge.client.game.rules.Rules;
import com.zuehlke.jasschallenge.client.game.rules.TopDownRules;

public enum Mode {
    OBEABE(new TopDownRules());

    private final Rules rules;

    Mode(Rules rules) {
        this.rules = rules;
    }

    public Rules getRules() {
        return rules;
    }
}
