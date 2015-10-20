package com.zuehlke.jasschallenge.client.websocket;

interface IConnection<M> {

    void send(M message);

}
