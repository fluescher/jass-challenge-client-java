package com.zuehlke.jasschallenge.client;

import com.zuehlke.jasschallenge.client.game.Player;
import com.zuehlke.jasschallenge.client.websocket.RemoteGameHandler;
import com.zuehlke.jasschallenge.client.websocket.RemoteGameSocket;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.concurrent.TimeUnit;

public class RemoteGame {

    private final static Logger logger = LoggerFactory.getLogger(RemoteGame.class);
    private final Player player;
    private final String targetUrl;

    public RemoteGame(String targetUrl, Player player) {
        this.targetUrl = targetUrl;
        this.player = player;
    }

    public void start() throws Exception {
                WebSocketClient client = new WebSocketClient();
        try {
            RemoteGameSocket socket = new RemoteGameSocket(new RemoteGameHandler(player));
            client.start();

            URI uri = new URI(targetUrl);
            ClientUpgradeRequest request = new ClientUpgradeRequest();
            client.connect(socket, uri, request);
            logger.debug("Connecting to: {}", uri);
            socket.awaitClose(5, TimeUnit.MINUTES);
        } finally {
            client.stop();
        }
    }

}
