package ru.peef.mglegendsoflevel.network.sockets;

import java.util.List;

public class MessageType {
    public static MessageType SHOW_SCREEN = new MessageType("ShowScreen");
    private final String text;
    public MessageType(String text) {
        this.text = text;
    }
    public String getPrefix() { return this.text; }
}
