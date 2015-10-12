package com.zuehlke.jasschallenge.client.websocket;

import com.zuehlke.jasschallenge.client.game.Player;
import com.zuehlke.jasschallenge.client.websocket.messages.type.RemotePlayer;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class PlayerMapper {

    private final Set<Player> allPlayers = new HashSet<>();

    public PlayerMapper(Player localPlayer) {
        allPlayers.add(localPlayer);
    }

    public Player mapPlayer(RemotePlayer remotePlayer) {
        Player player = tryToFindPlayerById(remotePlayer.getId()).orElse(new Player(remotePlayer.getId(), remotePlayer.getName()));
        allPlayers.add(player);
        return player;
    }

    public Player findPlayerById(int id) {
        return tryToFindPlayerById(id)
                .orElseThrow(() -> new RuntimeException("No Player with name " + id + " found"));
    }

    private Optional<Player> tryToFindPlayerById(int id) {
        return allPlayers.stream()
                .filter(player -> player.getId() == id)
                .findFirst();
    }
}
