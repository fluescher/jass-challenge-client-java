package com.zuehlke.jasschallenge.client.game.cards;

import java.util.Arrays;
import java.util.List;

import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.toList;

public class Card {

    public static final List<Card> ALL_CARDS = unmodifiableList(generateAllCards());

    private static List<Card> generateAllCards() {
        return Arrays
                .stream(Color.values())
                .flatMap(color -> Arrays.stream(CardValue.values()).map(value -> new Card(value, color)))
                        .collect(toList());
    }

    private final CardValue value;
    private final Color color;

    public Card(CardValue value, Color color) {
        this.value = value;
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "Card{" +
                "value=" + value +
                ", color=" + color +
                '}';
    }
}
