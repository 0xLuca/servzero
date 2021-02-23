package net.servzero.server.game;

public enum EnumDifficulty {
    PEACEFUL(0),
    EASY(1),
    NORMAL(2),
    HARD(3)
    ;

    private final byte id;

    EnumDifficulty(int id) {
        this.id = (byte) id;
    }

    public byte getId() {
        return id;
    }
}
