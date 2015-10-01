package com.zuehlke.jasschallenge.client.game.cards;

import static com.zuehlke.jasschallenge.client.game.cards.CardValue.*;
import static com.zuehlke.jasschallenge.client.game.cards.Color.*;

public enum Card {
    HEART_SIX(HEARTS, SIX),
    HEART_SEVEN(HEARTS, SEVEN),
    HEART_EIGHT(HEARTS, EIGHT),
    HEART_NINE(HEARTS, NINE),
    HEART_TEN(HEARTS, TEN),
    HEART_JACK(HEARTS, JACK),
    HEART_QUEEN(HEARTS, QUEEN),
    HEART_KING(HEARTS, KING),
    HEART_ACE(HEARTS, ACE),

    DIAMOND_SIX(DIAMONDS, SIX),
    DIAMOND_SEVEN(DIAMONDS, SEVEN),
    DIAMOND_EIGHT(DIAMONDS, EIGHT),
    DIAMOND_NINE(DIAMONDS, NINE),
    DIAMOND_TEN(DIAMONDS, TEN),
    DIAMOND_JACK(DIAMONDS, JACK),
    DIAMOND_QUEEN(DIAMONDS, QUEEN),
    DIAMOND_KING(DIAMONDS, KING),
    DIAMOND_ACE(DIAMONDS, ACE),

    CLUB_SIX(CLUBS, SIX),
    CLUB_SEVEN(CLUBS, SEVEN),
    CLUB_EIGHT(CLUBS, EIGHT),
    CLUB_NINE(CLUBS, NINE),
    CLUB_TEN(CLUBS, TEN),
    CLUB_JACK(CLUBS, JACK),
    CLUB_QUEEN(CLUBS, QUEEN),
    CLUB_KING(CLUBS, KING),
    CLUB_ACE(CLUBS, ACE),

    SPADE_SIX(SPADES, SIX),
    SPADE_SEVEN(SPADES, SEVEN),
    SPADE_EIGHT(SPADES, EIGHT),
    SPADE_NINE(SPADES, NINE),
    SPADE_TEN(SPADES, TEN),
    SPADE_JACK(SPADES, JACK),
    SPADE_QUEEN(SPADES, QUEEN),
    SPADE_KING(SPADES, KING),
    SPADE_ACE(SPADES, ACE);

    private final CardValue value;
    private final Color color;

    Card(Color color, CardValue value) {
        this.value = value;
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public CardValue getValue() { return value; }

    public boolean isHigherThan(Card other) {
        return color == other.color && value.getRank() > other.value.getRank();
    }

    public boolean isHigherTrumpfThan(Card other) {
        return value.getTrumpfRank() > other.getValue().getTrumpfRank();
    }

    @Override
    public String toString() {
        return "Card{" +
                "value=" + value +
                ", color=" + color +
                '}';
    }
}
