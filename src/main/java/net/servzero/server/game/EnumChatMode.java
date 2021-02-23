package net.servzero.server.game;

import java.util.Arrays;

public enum EnumChatMode {
    ENABLED(0),
    COMMANDS_ONLY(1),
    HIDDEN(2)
    ;

    private final int id;

    EnumChatMode(int id) {
        this.id = id;
    }

    public static EnumChatMode getById(int id) {
        return values()[id];
    }

    public int getId() {
        return id;
    }
}
