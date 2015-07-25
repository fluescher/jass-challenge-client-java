package com.zuehlke.jasschallenge.client.websocket.messages;

import com.zuehlke.jasschallenge.client.websocket.messages.type.RemoteCard;

import java.util.List;

public class DealCard {
    private String type;
    private List<RemoteCard> data;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<RemoteCard> getData() {
        return data;
    }

    public void setData(List<RemoteCard> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "DealCard{" +
                "type='" + type + '\'' +
                ", data=" + data +
                '}';
    }
}
