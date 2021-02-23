package net.servzero.chat;

public class ChatComponent {
    public static String get(String message) {
        return "{\"text:\" \"" + message + "\"}";
    }
}
