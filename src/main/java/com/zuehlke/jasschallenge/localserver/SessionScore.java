package com.zuehlke.jasschallenge.localserver;


import com.zuehlke.jasschallenge.messages.type.RemoteTeam;

import java.util.Arrays;
import java.util.List;

class SessionScore {

    private final RemoteTeam team1;
    private final RemoteTeam team2;

    private final int scoreLimit;
    private RemoteTeam winnerTeam;

    public SessionScore(RemoteTeam team1, RemoteTeam team2, int scoreLimit) {
        this.team1 = team1;
        this.team2 = team2;
        this.scoreLimit = scoreLimit;
    }

    public boolean winnerExists() {
        return team1.getCurrentRoundPoints() + team2.getPoints() >= scoreLimit || team2.getCurrentRoundPoints() + team1.getPoints() >= scoreLimit;
    }

    public void appendScore(int points, RemoteTeam teamOf) {
        RemoteTeam t = teamOf.getName().equals(team1.getName()) ? team1 : team2;
        t.setCurrentRoundPoints(t.getCurrentRoundPoints() + points);
    }

    public List<RemoteTeam> getTeams() {
        return Arrays.asList(team1, team2);
    }

    public void updateRoundScores() {
        team1.setPoints(team1.getPoints() + team1.getCurrentRoundPoints());
        team2.setPoints(team2.getPoints() + team2.getCurrentRoundPoints());
        team1.setCurrentRoundPoints(0);
        team2.setCurrentRoundPoints(0);
    }

    public RemoteTeam getWinnerTeam() {
        return getTotalScore(team1) > getTotalScore(team2) ? team1 : team2;
    }

    private int getTotalScore(RemoteTeam team1) {
        return team1.getPoints() + team1.getCurrentRoundPoints();
    }
}
