package com.zuehlke.jasschallenge.client.websocket.messages;

import com.zuehlke.jasschallenge.client.websocket.messages.type.RemoteCard;

import java.util.List;

public class PlayedCards extends Message {

    private List<RemoteCard> data;

    public PlayedCards() {
    }

    public PlayedCards(List<RemoteCard> data) {
        this.data = data;
    }

    public List<RemoteCard> getData() {
        return data;
    }

    public void setData(List<RemoteCard> data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlayedCards that = (PlayedCards) o;

        return !(data != null ? !data.equals(that.data) : that.data != null);

    }

    @Override
    public int hashCode() {
        return data != null ? data.hashCode() : 0;
    }
}
