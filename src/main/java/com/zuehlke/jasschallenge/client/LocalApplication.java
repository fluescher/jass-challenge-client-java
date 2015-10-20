package com.zuehlke.jasschallenge.client;

import com.zuehlke.jasschallenge.client.game.Player;
import com.zuehlke.jasschallenge.client.game.strategy.AlwaysShiftRandomJassStrategy;
import com.zuehlke.jasschallenge.client.game.strategy.RandomJassStrategy;
import com.zuehlke.jasschallenge.messages.type.SessionType;

public class LocalApplication {
    public static void main(String[] args) throws Exception {
        final Player myLocalPlayer = new Player("Me", new AlwaysShiftRandomJassStrategy());
        final Player myLocalPartner = new Player("My Partner", new AlwaysShiftRandomJassStrategy());
        final Player myLocalOpponent1 = new Player("Opponent 1", new RandomJassStrategy());
        final Player myLocalOpponent2 = new Player("Opponent 2", new RandomJassStrategy());

        startGame(myLocalPlayer, myLocalPartner, myLocalOpponent1, myLocalOpponent2, SessionType.SINGLE_GAME);
    }

    private static void startGame(Player myLocalPlayer, Player myLocalPartner, Player myOpponent1, Player myOpponent2, SessionType sessionType) throws Exception {
        final Game localGame = new LocalGame(myLocalPlayer, myLocalPartner, myOpponent1, myOpponent2, sessionType);
        localGame.start();
    }


}
