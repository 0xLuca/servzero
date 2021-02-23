package net.servzero.server.game;

public enum EnumDimension {
    NETHER(-1),
    OVERWORLD(0),
    END(1)
    ;

    private final int id;

    EnumDimension(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
