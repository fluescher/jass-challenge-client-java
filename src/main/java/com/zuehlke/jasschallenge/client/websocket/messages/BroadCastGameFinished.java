package com.zuehlke.jasschallenge.client.websocket.messages;

import com.zuehlke.jasschallenge.client.websocket.messages.type.Team;

import java.util.List;

public class BroadCastGameFinished extends Message {

    private List<Team> data;

    public BroadCastGameFinished() {
    }

    public BroadCastGameFinished(List<Team> teams) {
        this.data = teams;
    }

    public List<Team> getData() {
        return data;
    }

    public void setData(List<Team> data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BroadCastGameFinished that = (BroadCastGameFinished) o;

        return !(data != null ? !data.equals(that.data) : that.data != null);

    }

    @Override
    public int hashCode() {
        return data != null ? data.hashCode() : 0;
    }
}
