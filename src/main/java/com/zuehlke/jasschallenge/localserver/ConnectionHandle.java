package com.zuehlke.jasschallenge.localserver;

import com.zuehlke.jasschallenge.client.websocket.GameSocket;
import com.zuehlke.jasschallenge.client.websocket.ResponseChannel;
import com.zuehlke.jasschallenge.messages.Message;
import com.zuehlke.jasschallenge.messages.responses.Response;

import java.io.IOException;
import java.util.Optional;

class ConnectionHandle {

    private final GameSocket gameSocket;

    ConnectionHandle(GameSocket gameSocket) {
        this.gameSocket = gameSocket;
    }

    public void send(Message message) {
        try {
            gameSocket.onMessage(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public <T extends Response> T ask(Message message, Class<T> responseClass) {
        DirectResponseChannel responseChannel = new DirectResponseChannel();
        GameSocket socket = new GameSocketWrapper(gameSocket, responseChannel);
        try {
            socket.onMessage(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //noinspection unchecked
        return (T)responseChannel.getRespose();
    }

    private class DirectResponseChannel implements ResponseChannel {
        private Response respose;

        @Override
        public void respond(Response response) {
            this.respose = response;
        }

        public Response getRespose() {
            return respose;
        }
    }



    private class GameSocketWrapper extends GameSocket {

        private GameSocket gameSocket;

        public GameSocketWrapper(GameSocket gameSocket, ResponseChannel responseChannel) {
            super(null);
            this.gameSocket = gameSocket;
            onConnect(responseChannel);
        }

        @Override
        public void onMessage(Message msg) throws IOException {
            Optional<Response> response = gameSocket.dispatchMessage(msg);
            response.ifPresent(responseChannel::respond);
        }
    }
}