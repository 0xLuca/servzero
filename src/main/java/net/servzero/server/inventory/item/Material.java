package net.servzero.server.inventory.item;

public enum Material {
    AIR(0),
    STONE(1)
    ;

    private int id;

    Material(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
