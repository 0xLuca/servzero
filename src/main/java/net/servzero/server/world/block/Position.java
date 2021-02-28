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

    public Position addX(int x) {
        return Position.get(this.x + x, this.y, this.z);
    }

    public Position addY(int y) {
        return Position.get(this.x, this.y + y, this.z);
    }

    public Position addZ(int z) {
        return Position.get(this.x, this.y, this.z + z);
    }

    public long toSerializedPosition() {
        return ((long) (this.x & 0x3FFFFFF) << 38) | ((long) (this.y & 0xFFF) << 26) | (this.z & 0x3FFFFFF);
    }

    public static Position fromSerializedPosition(long serialized) {
        int x = (int) (serialized >> 38);
        int y = (int) ((serialized >> 26) & 0xFFF);
        int z = (int) (serialized << 38 >> 38);
        return Position.get(x, y, z);
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
