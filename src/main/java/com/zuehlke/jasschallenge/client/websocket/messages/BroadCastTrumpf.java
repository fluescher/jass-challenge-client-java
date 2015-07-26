package com.zuehlke.jasschallenge.client.websocket.messages;

import com.zuehlke.jasschallenge.client.websocket.messages.type.TrumpfChoice;

public class BroadCastTrumpf extends Message {
    private TrumpfChoice data;

    public BroadCastTrumpf() {
    }

    public BroadCastTrumpf(TrumpfChoice trumpfChoice) {
        this.data = trumpfChoice;
    }

    public TrumpfChoice getData() {
        return data;
    }

    public void setData(TrumpfChoice data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BroadCastTrumpf that = (BroadCastTrumpf) o;

        return !(data != null ? !data.equals(that.data) : that.data != null);

    }

    @Override
    public int hashCode() {
        return data != null ? data.hashCode() : 0;
    }
}
