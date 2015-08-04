package com.zuehlke.jasschallenge.client.websocket.messages.responses;

import com.zuehlke.jasschallenge.client.websocket.messages.type.SessionChoice;
import com.zuehlke.jasschallenge.client.websocket.messages.type.SessionType;

public class ChooseSession implements Response {
    private final SessionChoice data;

    public ChooseSession(SessionType sessionType) {
        data = new SessionChoice(sessionType);
    }

    public SessionChoice getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChooseSession that = (ChooseSession) o;

        return !(data != null ? !data.equals(that.data) : that.data != null);

    }

    @Override
    public int hashCode() {
        return data != null ? data.hashCode() : 0;
    }
}
