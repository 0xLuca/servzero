package net.servzero.server.entity;

public class DeltaPosition {
    private final int deltaX;
    private final int deltaY;
    private final int deltaZ;

    public DeltaPosition(int deltaX, int deltaY, int deltaZ) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.deltaZ = deltaZ;
    }

    public boolean isOverflow() {
        short max = Short.MAX_VALUE;
        return max < deltaX || max < deltaY || max < deltaZ;
    }

    public short getDeltaX() {
        return (short) deltaX;
    }

    public short getDeltaY() {
        return (short) deltaY;
    }

    public short getDeltaZ() {
        return (short) deltaZ;
    }
}
