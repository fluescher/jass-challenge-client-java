package com.zuehlke.jasschallenge.client.game.cards;

public enum CardValue {
    SIX(1,1,0,0,11),
    SEVEN(2,0),
    EIGHT(3,8),
    NINE(4,12,0,14,0),
    TEN(5,10),
    JACK(6,13,2,20,2),
    QUEEN(7,3),
    KING(8,4),
    ACE(9,9,11,11,0);

    private final int rank;
    private final int trumpfRank;
    private final int score;
    private final int trumpfScore;
    private final int bottomUpScore;

    CardValue(int rank, int score) {
        this(rank, rank, score, score, score);
    }

    CardValue(int rank, int trumpfRank, int score, int trumpfScore, int bottomUpScore) {
        this.rank = rank;
        this.trumpfRank = trumpfRank;
        this.score = score;
        this.trumpfScore = trumpfScore;
        this.bottomUpScore = bottomUpScore;
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

    public int getBottomUpScore() {
        return bottomUpScore;
    }
}
