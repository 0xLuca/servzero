package net.servzero.chat;

public enum EnumChatType {
    CHAT(0),
    SYSTEM(1),
    GAME_INFO(2)
    ;

    private byte id;

    EnumChatType(int id) {
        this.id = (byte) id;
    }

    public byte getId() {
        return id;
    }
}
