package com.zuehlke.jasschallenge.client.game.cards;

public enum CardValue {
    SIX(1,0),
    SEVEN(2,0),
    EIGHT(3,0),
    NINE(4,0),
    TEN(5,10),
    JACK(6,2),
    QUEEN(7,3),
    KING(8,4),
    ACE(9,11);

    private final int rank;
    private final int value;

    CardValue(int rank, int value) {
        this.rank = rank;
        this.value = value;
    }
}
