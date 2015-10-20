package com.zuehlke.jasschallenge.client.websocket;

public interface IConnection<M> {

    void send(M message);

}
