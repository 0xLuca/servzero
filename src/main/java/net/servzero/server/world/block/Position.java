package net.servzero.server.world.block;

public class Position {
    private int x;
    private int y;
    private int z;

    private Position(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static Position get(int x, int y, int z) {
        return new Position(x, y, z);
    }

    public static Position get(double x, double y, double z) {
        return get((int) x, (int) y, (int) z);
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return this.z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public Position toChunkCoord() {
        return Position.get((int) Math.floor(this.x / 16.0), (int) Math.floor(this.y / 16.0), (int) Math.floor(this.z / 16.0));
    }

    public long toSerializedPosition() {
        return ((long) (this.x & 0x3FFFFFF) << 38) | ((long) (this.y & 0xFFF) << 26) | (this.z & 0x3FFFFFF);
    }
}
