package com.zuehlke.jasschallenge.client.websocket.messages;

import com.zuehlke.jasschallenge.client.websocket.messages.type.RemoteCard;

public class RejectCard extends Message {
    private RemoteCard data;

    public RejectCard() {
    }

    public RejectCard(RemoteCard remoteCard) {
        this.data = remoteCard;
    }

    public RemoteCard getData() {
        return data;
    }

    public void setData(RemoteCard data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RejectCard that = (RejectCard) o;

        return !(data != null ? !data.equals(that.data) : that.data != null);

    }

    @Override
    public int hashCode() {
        return data != null ? data.hashCode() : 0;
    }
}
