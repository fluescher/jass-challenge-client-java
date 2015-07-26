package com.zuehlke.jasschallenge.client.websocket.messages.type;

public class Team {
    private String name;
    private int points;
    private int currentRoundPoints;

    public Team() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Team team = (Team) o;

        if (points != team.points) return false;
        if (currentRoundPoints != team.currentRoundPoints) return false;
        return !(name != null ? !name.equals(team.name) : team.name != null);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + points;
        result = 31 * result + currentRoundPoints;
        return result;
    }
}
