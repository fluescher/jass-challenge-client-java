package com.zuehlke.jasschallenge.localserver;

import com.zuehlke.jasschallenge.client.websocket.ResponseChannel;
import com.zuehlke.jasschallenge.messages.responses.Response;

class PlayerToLocalServerResponseChannel implements ResponseChannel {
    private final Player player;
    private final LocalServer localServer;

    public PlayerToLocalServerResponseChannel(Player player, LocalServer localServer) {
        this.player = player;
        this.localServer = localServer;
    }

    @Override
    public void respond(Response response) {
        localServer.onResponse(response, player);
    }
}
