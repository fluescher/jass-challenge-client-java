package com.zuehlke.jasschallenge.client;

import com.zuehlke.jasschallenge.client.game.Mode;
import com.zuehlke.jasschallenge.client.game.Player;
import com.zuehlke.jasschallenge.client.game.strategy.RandomMoveStrategy;
import com.zuehlke.jasschallenge.client.game.strategy.StrategySelector;

public class Application {
    public static void main(String[] args) throws Exception {
        final StrategySelector strategySelector = new StrategySelector(cards -> Mode.OBEABE, m -> new RandomMoveStrategy());
        final String name = String.valueOf(System.currentTimeMillis());
        final Player myLocalPlayer = new Player(name, strategySelector);

        final RemoteGame remoteGame = new RemoteGame("ws://localhost:3000", myLocalPlayer);
        remoteGame.start();
    }
}
