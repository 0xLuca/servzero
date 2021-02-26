package net.servzero.server.game;

public enum EnumEntityAction {
    START_SNEAKING(0),
    STOP_SNEAKING(1),
    LEAVE_BED(2),
    START_SPRINTING(3),
    STOP_SPRINTING(4),
    START_HORSEJUMP(5),
    STOP_HORSEJUMP(6),
    OPEN_HORSE_INVENTORY(7),
    START_ELYTRA_FLIGHT(8)
    ;

    private final int id;

    EnumEntityAction(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
