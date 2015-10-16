package com.zuehlke.jasschallenge.client.websocket;

import com.zuehlke.jasschallenge.client.websocket.messages.responses.Response;

public interface ResponseChannel {

    void respond(Response response);

}
