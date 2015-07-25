package com.zuehlke.jasschallenge.client.game.cards;

import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;

public class CardGenerator extends Generator<Card> {

    public CardGenerator() {
        super(Card.class);
    }

    @Override
    public Card generate(SourceOfRandomness random, GenerationStatus status) {
        final int randomCardIndex = random.nextInt(0, Card.values().length - 1);
        return Card.values()[randomCardIndex];
    }
}
