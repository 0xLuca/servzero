package net.servzero.server.entity;

public class DeltaPosition {
    private final short deltaX;
    private final short deltaY;
    private final short deltaZ;

    public DeltaPosition(short deltaX, short deltaY, short deltaZ) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.deltaZ = deltaZ;
    }

    public short getDeltaX() {
        return deltaX;
    }

    public short getDeltaY() {
        return deltaY;
    }

    public short getDeltaZ() {
        return deltaZ;
    }
}
