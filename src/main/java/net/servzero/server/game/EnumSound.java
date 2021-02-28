package net.servzero.server.game;

public enum EnumSound {
    BLOCK_STONE_BREAK(104),
    BLOCK_STONE_PLACE(107)
    ;

    private int id;

    EnumSound(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
