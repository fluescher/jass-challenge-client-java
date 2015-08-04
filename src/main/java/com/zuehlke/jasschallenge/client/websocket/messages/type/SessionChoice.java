package com.zuehlke.jasschallenge.client.websocket.messages.type;

public class SessionChoice {
    private final SessionType sessionChoice;
    private final String sessionName;

    public SessionChoice(SessionType sessionChoice, String sessionName) {
        this.sessionChoice = sessionChoice;
        this.sessionName = sessionName;
    }

    public SessionType getSessionChoice() {
        return sessionChoice;
    }

    public String getSessionName() {
        return sessionName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SessionChoice that = (SessionChoice) o;

        if (sessionChoice != that.sessionChoice) return false;
        return !(sessionName != null ? !sessionName.equals(that.sessionName) : that.sessionName != null);

    }

    @Override
    public int hashCode() {
        int result = sessionChoice != null ? sessionChoice.hashCode() : 0;
        result = 31 * result + (sessionName != null ? sessionName.hashCode() : 0);
        return result;
    }
}
