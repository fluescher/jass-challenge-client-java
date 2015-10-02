package com.zuehlke.jasschallenge.client.game;

public class Result {
    private final TeamPoints teamAPoints;
    private final TeamPoints teamBPoints;

    public Result(Team teamA, Team teamB) {
        this.teamAPoints = new TeamPoints(teamA);
        this.teamBPoints = new TeamPoints(teamB);
    }

    public int getTeamScore(Player player) {
        return getTeamPointsForPlayer(player).getScore();
    }

    public void updateWinningTeamScore(int bonusScore) {
        final Team winningTeam = getWinningTeam();
        updateTeamScore(winningTeam.getPlayers().get(0), bonusScore);
    }

    public boolean isMatch() {
        return teamAPoints.getScore() == 0 || teamBPoints.getScore() == 0;
    }

    private Team getWinningTeam() {

        if(teamAPoints.getScore() > teamBPoints.getScore()) {
            return teamAPoints.getTeam();
        } else {
            return teamBPoints.getTeam();
        }
    }

    void updateTeamScore(Player winningPlayer, int lastScore) {
        final TeamPoints teamScore = getTeamPointsForPlayer(winningPlayer);
        teamScore.addScore(lastScore);
    }

    private TeamPoints getTeamPointsForPlayer(Player player) {
        if(teamAPoints.getTeam().isTeamOfPlayer(player)) return teamAPoints;
        else return teamBPoints;
    }

    private TeamPoints getTeamPointsForTeam(Team team) {
        if (teamAPoints.getTeam().equals(team)) return teamAPoints;
        else return teamBPoints;
    }

    private static class TeamPoints {
        private final Team team;
        private int score;

        public TeamPoints(Team team) {
            this.team = team;
            this.score = 0;
        }

        void addScore(int score) {
            this.score += score;
        }

        public int getScore() {
            return score;
        }

        public Team getTeam() {
            return team;
        }
    }
}
