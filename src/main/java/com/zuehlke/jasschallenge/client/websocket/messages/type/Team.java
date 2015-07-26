package com.zuehlke.jasschallenge.client.websocket.messages.type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Team {
    private String name;
    private int points;
    private int currentRoundPoints;
    private List<RemotePlayer> players;

    public Team() {
    }

    public Team(String name, List<RemotePlayer> players) {
        this.players = players;
        this.name = name;
    }

    public Team(String name, int points, int currentRoundPoints) {
        this.name = name;
        this.points = points;
        this.currentRoundPoints = currentRoundPoints;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getCurrentRoundPoints() {
        return currentRoundPoints;
    }

    public void setCurrentRoundPoints(int currentRoundPoints) {
        this.currentRoundPoints = currentRoundPoints;
    }

    public List<RemotePlayer> getPlayers() {
        return players;
    }

    public void setPlayers(List<RemotePlayer> players) {
        this.players = players;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Team team = (Team) o;

        if (points != team.points) return false;
        if (currentRoundPoints != team.currentRoundPoints) return false;
        if (name != null ? !name.equals(team.name) : team.name != null) return false;
        return !(players != null ? !players.equals(team.players) : team.players != null);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + points;
        result = 31 * result + currentRoundPoints;
        result = 31 * result + (players != null ? players.hashCode() : 0);
        return result;
    }
}
