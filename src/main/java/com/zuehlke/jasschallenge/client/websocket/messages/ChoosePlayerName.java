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
}
