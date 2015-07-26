package com.zuehlke.jasschallenge.client.websocket.messages.type;

public class TrumpfChoice {
    private Trumpf mode;
    private RemoteColor trumpfColor;

    public TrumpfChoice() {
    }

    public TrumpfChoice(Trumpf trumpf, RemoteColor trumpfColor) {
        this.mode = trumpf;
        this.trumpfColor = trumpfColor;
    }

    public void setMode(Trumpf mode) {
        this.mode = mode;
    }

    public void setTrumpfColor(RemoteColor trumpfColor) {
        this.trumpfColor = trumpfColor;
    }

    public Trumpf getMode() {
        return mode;
    }

    public RemoteColor getTrumpfColor() {
        return trumpfColor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TrumpfChoice that = (TrumpfChoice) o;

        if (mode != that.mode) return false;
        return trumpfColor == that.trumpfColor;

    }

    @Override
    public int hashCode() {
        int result = mode != null ? mode.hashCode() : 0;
        result = 31 * result + (trumpfColor != null ? trumpfColor.hashCode() : 0);
        return result;
    }
}
