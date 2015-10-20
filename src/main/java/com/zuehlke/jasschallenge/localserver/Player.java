package com.zuehlke.jasschallenge.localserver;


import com.zuehlke.jasschallenge.messages.Message;
import com.zuehlke.jasschallenge.messages.responses.Response;

class Player {

    private ConnectionHandle connectionHandle;
    private String playerName;
    private int id;

    public Player(ConnectionHandle connectionHandle) {
        this.connectionHandle = connectionHandle;
    }

    public void notify(Message message) {
        connectionHandle.send(message);
    }

    public String getName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public <T extends Response> T ask(Message message, Class<T> responseClass) {
        return connectionHandle.ask(message, responseClass);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Player{" +
                "playerName='" + playerName + '\'' +
                ", id=" + id +
                '}';
    }
}
