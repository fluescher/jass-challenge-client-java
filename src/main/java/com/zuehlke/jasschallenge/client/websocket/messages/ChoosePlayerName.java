package com.zuehlke.jasschallenge.client.websocket.messages;

public class ChoosePlayerName extends Message {
    private final String data;

    public ChoosePlayerName(String data) {
        super(Type.CHOOSE_PLAYER_NAME);
        this.data = data;
    }

    public String getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChoosePlayerName that = (ChoosePlayerName) o;

        return !(data != null ? !data.equals(that.data) : that.data != null);

    }

    @Override
    public int hashCode() {
        return data != null ? data.hashCode() : 0;
    }
}
