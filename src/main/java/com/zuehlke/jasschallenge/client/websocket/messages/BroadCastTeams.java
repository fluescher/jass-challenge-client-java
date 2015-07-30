package com.zuehlke.jasschallenge.client.websocket.messages;

import com.zuehlke.jasschallenge.client.websocket.messages.type.RemoteTeam;

import java.util.List;

public class BroadCastTeams extends Message {
    private List<RemoteTeam> data;

    public BroadCastTeams() {
    }

    public BroadCastTeams(List<RemoteTeam> remoteTeams) {
        data = remoteTeams;
    }

    public List<RemoteTeam> getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BroadCastTeams that = (BroadCastTeams) o;

        return !(data != null ? !data.equals(that.data) : that.data != null);

    }

    @Override
    public int hashCode() {
        return data != null ? data.hashCode() : 0;
    }
}
