package com.zuehlke.jasschallenge.client.websocket.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zuehlke.jasschallenge.client.websocket.RemoteGameHandler;
import com.zuehlke.jasschallenge.client.websocket.messages.responses.Response;
import com.zuehlke.jasschallenge.client.websocket.messages.type.RemoteCard;

import java.util.List;
import java.util.Optional;

public class DealCard implements Message {
    private final List<RemoteCard> cards;

    public DealCard(@JsonProperty(value = "data",required = true) List<RemoteCard> cards) {
        this.cards = cards;
    }

    @Override
    public Optional<Response> dispatch(RemoteGameHandler handler) {
        handler.onDealCards(cards);
        return Optional.empty();
    }

    @Override
    public String toString() {
        return "DealCard{" +
                "cards=" + cards +
                '}';
    }
}
