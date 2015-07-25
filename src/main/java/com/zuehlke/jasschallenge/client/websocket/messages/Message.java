package com.zuehlke.jasschallenge.client.websocket.messages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {

    private Type type;

    public Message() {
    }

    public Message(Type choosePlayerName) {
        this.type = choosePlayerName;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public enum Type {
        REQUEST_PLAYER_NAME,
        REQUEST_SESSION_CHOICE,
        BROADCAST_SESSION_JOINED,
        BROADCAST_TEAMS,
        DEAL_CARDS,
        REQUEST_TRUMPF,
        CHOOSE_PLAYER_NAME,
        CHOOSE_SESSION,
        CHOOSE_TRUMPF,
        BROADCAST_WINNER_TEAM,
        BROADCAST_TRUMPF,
        BROADCAST_STICH,
        BROADCAST_GAME_FINISHED,
        CHOOSE_CARD,
        REQUEST_CARD,
        REJECT_CARD,
        PLAYED_CARDS
    }
}
