package com.zuehlke.jasschallenge.client.websocket.messages;

import com.zuehlke.jasschallenge.client.websocket.messages.type.RemotePlayer;

public class PlayerJoined extends Message {

    private RemotePlayer data;

    public PlayerJoined() {
    }

    public PlayerJoined(RemotePlayer remotePlayer) {
        data = remotePlayer;
    }

    public RemotePlayer getData() {
        return data;
    }

    public void setData(RemotePlayer data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlayerJoined that = (PlayerJoined) o;

        return !(data != null ? !data.equals(that.data) : that.data != null);

    }

    @Override
    public int hashCode() {
        return data != null ? data.hashCode() : 0;
    }
}
