package com.zuehlke.jasschallenge.client;

import com.zuehlke.jasschallenge.client.game.Player;
import com.zuehlke.jasschallenge.client.websocket.GameHandler;
import com.zuehlke.jasschallenge.client.websocket.GameSocket;
import com.zuehlke.jasschallenge.localserver.LocalServer;
import com.zuehlke.jasschallenge.messages.type.SessionType;

public class LocalGame implements Game {
    
    private final SessionType sessionType;

    private final LocalServer localServer = new LocalServer();
    private final Player player1;
    private final Player player2;
    private final Player player3;
    private final Player player4;

    public LocalGame(Player player1, Player player2, Player player3, Player player4, SessionType singleGame) {
        this.player1 = player1;
        this.player2 = player2;
        this.player3 = player3;
        this.player4 = player4;
        this.sessionType = singleGame;
    }

    @Override
    public void start() throws Exception {
        localServer.connect(new GameSocket(new GameHandler(player1, sessionType)));
        localServer.connect(new GameSocket(new GameHandler(player2, sessionType)));
        localServer.connect(new GameSocket(new GameHandler(player3, sessionType)));
        localServer.connect(new GameSocket(new GameHandler(player4, sessionType)));
    }
}
