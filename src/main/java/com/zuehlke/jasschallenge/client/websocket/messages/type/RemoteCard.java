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
    public String toString() {
        return "RemoteCard{" +
                "number=" + number +
                ", color=" + color +
                '}';
    }
}
