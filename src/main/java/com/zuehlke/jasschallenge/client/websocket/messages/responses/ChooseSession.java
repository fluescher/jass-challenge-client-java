package com.zuehlke.jasschallenge.client.websocket.messages.responses;

import com.zuehlke.jasschallenge.client.websocket.messages.type.ChooseSessionData;
import com.zuehlke.jasschallenge.client.websocket.messages.type.SessionChoice;
import com.zuehlke.jasschallenge.client.websocket.messages.type.SessionType;

public class ChooseSession implements Response {
    private final ChooseSessionData data;

    public ChooseSession(SessionChoice sessionChoice) {
        this(sessionChoice, "Java Client Session");
    }

    public ChooseSession(SessionChoice sessionChoice, String sessionName) {
        data = new ChooseSessionData(sessionChoice, sessionName, SessionType.TOURNAMENT);
    }

    public ChooseSessionData getData() {
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
