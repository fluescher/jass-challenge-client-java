package com.zuehlke.jasschallenge.client;

import com.zuehlke.jasschallenge.client.game.Player;

public class Application {
    public static void main(String[] args) throws Exception {
        final String targetUrl = "ws://localhost:3000";
        final Player myLocalPlayer = new Player(String.valueOf(System.currentTimeMillis()));
        final RemoteGame remoteGame = new RemoteGame(targetUrl, myLocalPlayer);
        remoteGame.start();
    }
}
