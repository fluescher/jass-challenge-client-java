package com.zuehlke.jasschallenge.messages.responses;

import com.zuehlke.jasschallenge.messages.type.RemoteCard;

public class ChooseCard implements Response {

    private final RemoteCard data;

    public ChooseCard(RemoteCard data) {
        this.data = data;
    }

    public RemoteCard getData() {
        return data;
    }
}
