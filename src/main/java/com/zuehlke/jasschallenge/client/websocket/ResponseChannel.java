package com.zuehlke.jasschallenge.client.websocket;

import com.zuehlke.jasschallenge.messages.responses.Response;

public interface ResponseChannel {

    void respond(Response response);

}
