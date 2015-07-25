package com.zuehlke.jasschallenge.client.websocket.messages;

public class ChooseSession extends Message {
    private final SessionChoice data;

    public ChooseSession(SessionType sessionType) {
        super(Type.CHOOSE_SESSION);
        data = new SessionChoice(sessionType);
    }

    public SessionChoice getData() {
        return data;
    }

    private class SessionChoice {
        private final SessionType sessionChoice;
        private final String sessionName = "a session";

        public SessionChoice(SessionType sessionChoice) {
            this.sessionChoice = sessionChoice;
        }

        public SessionType getSessionChoice() {
            return sessionChoice;
        }

        public String getSessionName() {
            return sessionName;
        }
    }

    public enum SessionType {
        AUTOJOIN
    }
}
