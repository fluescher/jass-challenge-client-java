package com.zuehlke.jasschallenge.messages.type;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Stich {
    private final String name;
    private final int id;
    private final List<RemoteCard> playedCards;
    private final List<RemoteTeam> teams;

    public Stich(@JsonProperty(value = "name",required = true) String name,
                 @JsonProperty(value = "id",required = true) int id,
                 @JsonProperty(value = "playedCards",required = true) List<RemoteCard> playedCards,
                 @JsonProperty(value = "teams",required = true) List<RemoteTeam> teams) {
        this.name = name;
        this.id = id;
        this.playedCards = playedCards;
        this.teams = teams;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public List<RemoteCard> getPlayedCards() {
        return playedCards;
    }

    public List<RemoteTeam> getTeams() {
        return teams;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Stich stich = (Stich) o;

        if (id != stich.id) return false;
        if (name != null ? !name.equals(stich.name) : stich.name != null) return false;
        if (playedCards != null ? !playedCards.equals(stich.playedCards) : stich.playedCards != null) return false;
        return !(teams != null ? !teams.equals(stich.teams) : stich.teams != null);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + id;
        result = 31 * result + (playedCards != null ? playedCards.hashCode() : 0);
        result = 31 * result + (teams != null ? teams.hashCode() : 0);
        return result;
    }
}
