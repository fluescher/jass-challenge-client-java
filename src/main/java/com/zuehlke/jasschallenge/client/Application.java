package com.zuehlke.jasschallenge.client;

import com.zuehlke.jasschallenge.client.game.Player;
import com.zuehlke.jasschallenge.client.game.strategy.RandomJassStrategy;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class Application {
    public static void main(String[] args) throws Exception {
        final String name = System.getProperty("name", String.valueOf(System.currentTimeMillis()));
        final Player myLocalPlayer = new Player(name, new RandomJassStrategy());
        final Player myLocalPartner = new Player(name, new RandomJassStrategy());

        startTournamentGame("ws://gamenighthacked.herokuapp.com", myLocalPlayer, myLocalPartner);
    }

    private static void startTournamentGame(String targetUrl, Player myLocalPlayer, Player myLocalPartner) throws Exception {
        Executors.newFixedThreadPool(2)
                .invokeAll(Arrays.asList(() -> startGame(targetUrl, myLocalPlayer),
                                         () -> startGame(targetUrl, myLocalPartner)))
                .stream()
                .forEach(f -> {
                    try {
                        f.get();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                });
    }

    private static RemoteGame startGame(String targetUrl, Player myLocalPlayer) throws Exception {

        final RemoteGame remoteGame = new RemoteGame(targetUrl, myLocalPlayer);
        remoteGame.start();
        return remoteGame;
    }
}
