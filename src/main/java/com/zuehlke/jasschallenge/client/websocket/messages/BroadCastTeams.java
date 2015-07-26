package com.zuehlke.jasschallenge.client.websocket.messages;

import com.zuehlke.jasschallenge.client.websocket.messages.type.Team;

import java.util.List;

public class BroadCastTeams extends Message {
    private List<Team> data;

    public BroadCastTeams() {
    }

    public BroadCastTeams(List<Team> teams) {
        data = teams;
    }

    public List<Team> getData() {
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
