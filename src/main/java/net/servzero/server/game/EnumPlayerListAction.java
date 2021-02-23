package net.servzero.server.game;

public enum EnumPlayerListAction {
    ADD_PLAYER(0),
    UPDATE_GAMEMODE(1),
    UPDATE_LATENCY(2),
    UPDATE_DISPLAY_NAME(3),
    REMOVE_PLAYER(4)
    ;

    private int id;

    EnumPlayerListAction(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
