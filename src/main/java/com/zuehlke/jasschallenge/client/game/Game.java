package com.zuehlke.jasschallenge.client.game;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private final List<Player> players;

    public Game() {
        this.players = new ArrayList<>();
    }

    public void joinPlayer(Player player) {
        players.add(player);
    }

    public Round startRound() {
        return Round.createRound(0);
    }
}
