package com.zuehlke.jasschallenge.client;

import com.zuehlke.jasschallenge.client.game.Player;
import com.zuehlke.jasschallenge.client.game.strategy.RandomJassStrategy;
import com.zuehlke.jasschallenge.messages.type.SessionType;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class Application {
    public static void main(String[] args) throws Exception {
        final String name = System.getProperty("name", String.valueOf(System.currentTimeMillis()));
        final Player myLocalPlayer = new Player(name, new RandomJassStrategy());

//        startTournamentGame("ws://localhost:3000", myLocalPlayer, myLocalPartner);
        startGame("ws://jasschallenge.herokuapp.com", myLocalPlayer, SessionType.SINGLE_GAME);
    }

    private static void startTournamentGame(String targetUrl, Player myLocalPlayer, Player myLocalPartner) throws Exception {
        final ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService
                .invokeAll(Arrays.asList(() -> startGame(targetUrl, myLocalPlayer, SessionType.TOURNAMENT),
                                         () -> startGame(targetUrl, myLocalPartner, SessionType.TOURNAMENT)))
                .stream()
                .forEach(Application::awaitFuture);
        executorService.shutdown();
    }

    private static <T> void awaitFuture(Future<T> future) {
        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static RemoteGame startGame(String targetUrl, Player myLocalPlayer, SessionType sessionType) throws Exception {

        final RemoteGame remoteGame = new RemoteGame(targetUrl, myLocalPlayer, sessionType);
        remoteGame.start();
        return remoteGame;
    }
}
