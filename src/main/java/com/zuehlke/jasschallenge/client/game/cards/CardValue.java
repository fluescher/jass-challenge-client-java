package com.zuehlke.jasschallenge.client.game.cards;

public enum CardValue {
    SIX(1,0),
    SEVEN(2,0),
    EIGHT(3,3,8,0),
    NINE(4,12,0,14),
    TEN(5,10),
    JACK(6,13,2,20),
    QUEEN(7,3),
    KING(8,4),
    ACE(9,11);

    private final int rank;
    private final int trumpfRank;
    private final int score;
    private final int trumpfScore;

    CardValue(int rank, int score) {
        this(rank, rank, score, score);
    }

    CardValue(int rank, int trumpfRank, int score, int trumpfScore) {
        this.rank = rank;
        this.trumpfRank = trumpfRank;
        this.score = score;
        this.trumpfScore = trumpfScore;
    }

    public int getScore() {
        return score;
    }

    public int getRank() {
        return rank;
    }

    public int getTrumpfRank() {
        return trumpfRank;
    }

    public int getTrumpfScore() {
        return trumpfScore;
    }
}
