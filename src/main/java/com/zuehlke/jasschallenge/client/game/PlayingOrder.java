package com.zuehlke.jasschallenge.client.game;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class PlayingOrder {

    private final List<Player> playersInInitialPlayingOrder;
    private final int startingPlayer;
    private int currentPlayer;

    public static PlayingOrder createOrder(List<Player> playersInInitialPlayingOrder) {
        return new PlayingOrder(playersInInitialPlayingOrder, 0);
    }

    public static PlayingOrder createOrderStartingFromPlayer(List<Player> playersInPlayingOrder, Player winner) {
        return new PlayingOrder(playersInPlayingOrder, playersInPlayingOrder.indexOf(winner));
    }

    private PlayingOrder(List<Player> playersInInitialPlayingOrder, int startingPlayer) {
        this.playersInInitialPlayingOrder = playersInInitialPlayingOrder;
        this.startingPlayer = startingPlayer;
        this.currentPlayer = 0;
    }

    public List<Player> getOrder() {
        return IntStream.range(0, 4)
                .mapToObj(i -> playersInInitialPlayingOrder.get(getBoundIndex(i)))
                .collect(toList());
    }

    public Player getCurrentPlayer() {
        return playersInInitialPlayingOrder.get(getBoundIndex(currentPlayer));
    }

    public void moveToNextPlayer() {
        currentPlayer = currentPlayer + 1;
    }

    public List<Player> getPlayersInInitialPlayingOrder() {
        return playersInInitialPlayingOrder;
    }

    private int getBoundIndex(int playerPosition) {
        return (this.startingPlayer + playerPosition) % 4;
    }
}
