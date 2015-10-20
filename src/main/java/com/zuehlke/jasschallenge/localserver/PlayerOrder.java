package com.zuehlke.jasschallenge.localserver;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

class PlayerOrder {

    private List<Player> order;
    private Player nextToPlay;

    public PlayerOrder(Player player1Team1, Player player1Team2, Player player2Team1, Player player2Team2) {
        order = asList(player1Team1, player1Team2, player2Team1, player2Team2);
        nextToPlay = player1Team1;
    }
    public Player getNextToPlay() {
        Player currentyNextToPlay = this.nextToPlay;
        int indexOfCurrent = order.indexOf(currentyNextToPlay);
        int nextIndex = ++indexOfCurrent;
        if (nextIndex == order.size()) {
            nextIndex = 0;
        }
        nextToPlay = order.get(nextIndex);
        return currentyNextToPlay;

    }


    public void stichMade(Player player) {
        nextToPlay = player;
    }

    public void newRound() {
        List<Player> newOrder = new ArrayList<>();
        int index = 1;
        while (newOrder.size() < order.size()) {
            newOrder.add(order.get(index++));
            if (index == order.size()) {
                index = 0;
            }
        }
        nextToPlay = newOrder.get(0);
        this.order = newOrder;
    }
}
