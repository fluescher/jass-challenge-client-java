package com.zuehlke.jasschallenge.client.websocket.messages;

import com.zuehlke.jasschallenge.client.websocket.messages.type.RemoteCard;

import java.util.List;

public class DealCard extends Message {
    private List<RemoteCard> data;

    public DealCard() {
    }

    public DealCard(List<RemoteCard> data) {
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

        DealCard dealCard = (DealCard) o;

        return !(data != null ? !data.equals(dealCard.data) : dealCard.data != null);

    }

    @Override
    public int hashCode() {
        return data != null ? data.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "DealCard{" +
                "data=" + data +
                '}';
    }
}
