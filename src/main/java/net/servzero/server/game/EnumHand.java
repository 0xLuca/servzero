package net.servzero.server.game;

public enum EnumHand {
    RIGHT(9),
    LEFT(1)
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