package com.zuehlke.jasschallenge.client.websocket.messages.type;

public class RemoteCard {

    private int number;
    private RemoteColor color;

    public RemoteCard() {
    }

    public RemoteCard(int number, RemoteColor color) {
        this.number = number;
        this.color = color;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public RemoteColor getColor() {
        return color;
    }

    public void setColor(RemoteColor color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RemoteCard that = (RemoteCard) o;

        if (number != that.number) return false;
        return color == that.color;

    }

    @Override
    public int hashCode() {
        int result = number;
        result = 31 * result + (color != null ? color.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RemoteCard{" +
                "number=" + number +
                ", color=" + color +
                '}';
    }
}
