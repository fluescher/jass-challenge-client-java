package com.zuehlke.jasschallenge.client.websocket.messages;

import com.zuehlke.jasschallenge.client.RemoteGame;
import com.zuehlke.jasschallenge.client.websocket.messages.type.RemoteColor;

public class ChooseTrumpf extends Message {

    private final TrumpfChoice data;

    public ChooseTrumpf(Trumpf trumpf) {
        super(Type.CHOOSE_TRUMPF);
        data = new TrumpfChoice(trumpf, null);
    }

    public TrumpfChoice getData() {
        return data;
    }

    public static class TrumpfChoice {
        private final Trumpf mode;
        private final RemoteColor trumpfColor;

        public TrumpfChoice(Trumpf trumpf, RemoteColor trumpfColor) {
            this.mode = trumpf;
            this.trumpfColor = trumpfColor;
        }

        public Trumpf getMode() {
            return mode;
        }

        public RemoteColor getTrumpfColor() {
            return trumpfColor;
        }
    }

    public enum Trumpf {
        OBEABE
    }
}
