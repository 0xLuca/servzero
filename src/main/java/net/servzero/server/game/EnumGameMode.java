package net.servzero.server.game;

public enum EnumGameMode {
    SURVIVAL(0),
    CREATIVE(1),
    ADVENTURE(2),
    SPECTATOR(3),
    NOT_SET(-1)
    ;

    private final byte id;

    EnumGameMode(int id) {
        this.id = (byte) id;
    }

    public byte getId() {
        return id;
    }
}
