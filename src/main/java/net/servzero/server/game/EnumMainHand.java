package net.servzero.server.game;

public enum EnumMainHand {
    LEFT(0),
    RIGHT(1)
    ;

    private final int id;

    EnumMainHand(int id) {
        this.id = id;
    }

    public static EnumMainHand getById(int id) {
        return values()[id];
    }

    public int getId() {
        return id;
    }
}