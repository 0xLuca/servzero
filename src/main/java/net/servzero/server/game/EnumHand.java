package net.servzero.server.game;

public enum EnumHand {
    LEFT(0),
    RIGHT(1)
    ;

    private final int id;

    EnumHand(int id) {
        this.id = id;
    }

    public static EnumHand getById(int id) {
        return values()[id];
    }

    public int getId() {
        return id;
    }
}