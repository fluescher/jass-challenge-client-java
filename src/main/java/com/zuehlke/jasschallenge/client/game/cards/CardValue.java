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
    private final int score;

    CardValue(int rank, int score) {
        this.rank = rank;
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public int getRank() {
        return rank;
    }
}
