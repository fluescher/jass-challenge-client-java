package com.zuehlke.jasschallenge.client.websocket.messages;

import com.zuehlke.jasschallenge.client.websocket.messages.type.RemoteCard;

import java.util.List;

public class PlayedCards extends Message {

    private List<RemoteCard> data;

    public List<RemoteCard> getData() {
        return data;
    }

    public void setData(List<RemoteCard> data) {
        this.data = data;
    }
}
