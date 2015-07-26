package com.zuehlke.jasschallenge.client.websocket.messages.type;

import java.util.List;

public class Stich {
    private String name;
    private int id;
    private List<RemoteCard> playedCards;
    private List<Team> teams;

    public Stich() {
    }

    public Stich(String name, int id, List<RemoteCard> playedCards, List<Team> teams) {
        this.name = name;
        this.id = id;
        this.playedCards = playedCards;
        this.teams = teams;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<RemoteCard> getPlayedCards() {
        return playedCards;
    }

    public void setPlayedCards(List<RemoteCard> playedCards) {
        this.playedCards = playedCards;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
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
