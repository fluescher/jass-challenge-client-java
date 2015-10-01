package com.zuehlke.jasschallenge.client;

import com.zuehlke.jasschallenge.client.game.Player;
import com.zuehlke.jasschallenge.client.game.strategy.RandomMoveJassStrategy;

public class Application {
    public static void main(String[] args) throws Exception {
        final String name = System.getProperty("name", String.valueOf(System.currentTimeMillis()));
        final Player myLocalPlayer = new Player(name, new RandomMoveJassStrategy());

        final RemoteGame remoteGame = new RemoteGame("ws://localhost:3000", myLocalPlayer);
        remoteGame.start();
    }
}
