package com.zuehlke.jasschallenge.client.websocket.messages;

import com.zuehlke.jasschallenge.client.websocket.messages.type.RemoteCard;

public class ChooseCard extends Message {

    private final RemoteCard data;

    public ChooseCard(RemoteCard data) {
        super(Type.CHOOSE_CARD);
        this.data = data;
    }

    public RemoteCard getData() {
        return data;
    }
}
