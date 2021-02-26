package net.servzero.server.world.block;

public class Coordinate {
    private int x;
    private int y;
    private int z;

    private Coordinate(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static Coordinate get(int x, int y, int z) {
        return new Coordinate(x, y, z);
    }

    public static Coordinate get(double x, double y, double z) {
        return get((int) x, (int) y, (int) z);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public Coordinate toChunkCoord() {
        return Coordinate.get((int) Math.floor(this.x/16.0), (int) Math.floor(this.y/16.0), (int) Math.floor(this.z/16.0));
    }
}
