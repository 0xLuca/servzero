package net.servzero.server.world.block;

public enum Material {
    AIR(0),
    STONE(1)
    ;

    private final int id;

    Material(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
