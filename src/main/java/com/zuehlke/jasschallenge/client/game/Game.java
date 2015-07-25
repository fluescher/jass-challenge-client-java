package com.zuehlke.jasschallenge.client.game;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private final List<Player> players;

    public Game(List<Player> player) {
        this.players = new ArrayList<>();
        player.forEach(this::joinPlayer);
    }

    public void joinPlayer(Player player) {
        players.add(player);
    }

    public Round startRound() {
        return Round.createRound(0);
    }
}
