package com.zuehlke.jasschallenge.client.websocket.messages.type;

import com.zuehlke.jasschallenge.game.cards.Color;

public enum RemoteColor {
    HEARTS(Color.HEARTS),
    DIAMONDS(Color.DIAMONDS),
    SPADES(Color.SPADES),
    CLUBS(Color.CLUBS);

    private final Color mappedColor;

    RemoteColor(Color mappedColor) {
        this.mappedColor = mappedColor;
    }

    public Color getMappedColor() {
        return mappedColor;
    }
}
