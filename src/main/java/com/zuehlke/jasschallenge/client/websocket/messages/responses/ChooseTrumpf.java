package com.zuehlke.jasschallenge.client.websocket.messages.responses;

import com.zuehlke.jasschallenge.client.websocket.messages.type.RemoteColor;
import com.zuehlke.jasschallenge.game.Trumpf;
import com.zuehlke.jasschallenge.client.websocket.messages.type.TrumpfChoice;

public class ChooseTrumpf implements Response {

    private final TrumpfChoice data;

    public ChooseTrumpf(Trumpf trumpf) {
        data = new TrumpfChoice(trumpf, null);
    }

    public ChooseTrumpf(Trumpf trumpf, RemoteColor color) {
        data = new TrumpfChoice(trumpf, color);
    }

    public TrumpfChoice getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChooseTrumpf that = (ChooseTrumpf) o;

        return !(data != null ? !data.equals(that.data) : that.data != null);

    }

    @Override
    public int hashCode() {
        return data != null ? data.hashCode() : 0;
    }
}
