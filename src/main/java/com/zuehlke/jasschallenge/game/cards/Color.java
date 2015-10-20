package com.zuehlke.jasschallenge.game.cards;

public enum Color {
    HEARTS("♥"),
    DIAMONDS("♦"),
    CLUBS("♣"),
    SPADES("♠");


    private final String sign;

    Color(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return sign;
    }
}
