package com.zuehlke.jasschallenge.client.websocket;

import com.zuehlke.jasschallenge.client.game.Player;
import com.zuehlke.jasschallenge.client.websocket.messages.type.RemotePlayer;

import java.util.*;

public class PlayerMapper {

    private final Set<Player> allPlayers = new HashSet<>();

    public PlayerMapper(Player localPlayer) {
        allPlayers.add(localPlayer);
    }

    public Player mapPlayer(RemotePlayer remotePlayer) {
        Player player = tryToFindPlayerByName(remotePlayer.getName()).orElse(new Player(remotePlayer.getName()));
        allPlayers.add(player);
        return player;
    }

    public Player findPlayerByName(String name) {
        return tryToFindPlayerByName(name)
                .orElseThrow(() -> new RuntimeException("No Player with name " + name + " found"));
    }

    private Optional<Player> tryToFindPlayerByName(String name) {
        return allPlayers.stream()
                .filter(player -> player.getName().equals(name))
                .findFirst();
    }
}
