package com.zuehlke.jasschallenge.client.websocket.messages;

import com.zuehlke.jasschallenge.client.websocket.messages.type.Team;

public class BroadCastWinnerTeam extends Message {
    private Team data;

    public BroadCastWinnerTeam() {
    }

    public BroadCastWinnerTeam(Team team) {
        this.data = team;
    }

    public Team getData() {
        return data;
    }

    public void setData(Team data) {
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
