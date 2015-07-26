package com.zuehlke.jasschallenge.client.websocket.messages;

import com.zuehlke.jasschallenge.client.websocket.messages.type.Stich;

public class BroadCastStich extends Message {

    private Stich data;

    public BroadCastStich() {
    }

    public BroadCastStich(Stich data) {
        this.data = data;
    }

    public Stich getData() {
        return data;
    }

    public void setData(Stich data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BroadCastStich that = (BroadCastStich) o;

        return !(data != null ? !data.equals(that.data) : that.data != null);

    }

    @Override
    public int hashCode() {
        return data != null ? data.hashCode() : 0;
    }
}
