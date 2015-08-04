package com.zuehlke.jasschallenge.client.websocket.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zuehlke.jasschallenge.client.websocket.RemoteGameHandler;
import com.zuehlke.jasschallenge.client.websocket.messages.responses.Response;
import com.zuehlke.jasschallenge.client.websocket.messages.type.RemoteCard;

import java.util.List;
import java.util.Optional;

public class PlayedCards implements Message {

    private final List<RemoteCard> playedCards;

    public PlayedCards(@JsonProperty(value = "data",required = true) List<RemoteCard> data) {
        this.playedCards = data;
    }

    @Override
    public Optional<Response> dispatch(RemoteGameHandler handler) {
        handler.onPlayedCards(playedCards);
        return Optional.empty();
    }
}
