package com.zuehlke.jasschallenge.client.websocket.messages.type;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RemotePlayer {
    private final int id;
    private final String name;

    public RemotePlayer(@JsonProperty(value = "id",required = true) int id,
                        @JsonProperty(value = "name",required = true) String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RemotePlayer that = (RemotePlayer) o;

        if (id != that.id) return false;
        return !(name != null ? !name.equals(that.name) : that.name != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
