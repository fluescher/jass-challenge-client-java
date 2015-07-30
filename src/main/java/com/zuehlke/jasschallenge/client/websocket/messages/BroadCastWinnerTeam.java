package com.zuehlke.jasschallenge.client.websocket.messages;

import com.zuehlke.jasschallenge.client.websocket.messages.type.RemoteTeam;

public class BroadCastWinnerTeam extends Message {
    private RemoteTeam data;

    public BroadCastWinnerTeam() {
    }

    public BroadCastWinnerTeam(RemoteTeam remoteTeam) {
        this.data = remoteTeam;
    }

    public RemoteTeam getData() {
        return data;
    }

    public void setData(RemoteTeam data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BroadCastWinnerTeam that = (BroadCastWinnerTeam) o;

        return !(data != null ? !data.equals(that.data) : that.data != null);

    }

    @Override
    public int hashCode() {
        return data != null ? data.hashCode() : 0;
    }
}
