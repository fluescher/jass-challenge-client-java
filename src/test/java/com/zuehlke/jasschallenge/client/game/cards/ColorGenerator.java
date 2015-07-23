package com.zuehlke.jasschallenge.client.game.cards;

import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;

public class ColorGenerator extends Generator<Color> {

    public ColorGenerator() {
        super(Color.class);
    }

    @Override
    public Color generate(SourceOfRandomness random, GenerationStatus status) {
        final int randomCardIndex = random.nextInt(0, Color.values().length - 1);
        return Color.values()[randomCardIndex];
    }
}
