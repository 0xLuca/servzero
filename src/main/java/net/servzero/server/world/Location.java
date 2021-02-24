package net.servzero.server.world;

public class Location {
    private final World world;
    private volatile double x;
    private volatile double y;
    private volatile double z;
    private volatile float yaw;
    private volatile float pitch;

    public Location(World world, double x, double y, double z) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Location(World world, double x, double y, double z, float yaw, float pitch) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public World getWorld() {
        return world;
    }

    public double getX() {
        return x;
    }

    public synchronized void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public synchronized void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public synchronized void setZ(double z) {
        this.z = z;
    }

    public float getYaw() {
        return yaw;
    }

    public synchronized void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public synchronized void setPitch(float pitch) {
        this.pitch = pitch;
    }

    @Override
    public String toString() {
        return "Location{" +
                "world=" + world +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", yaw=" + yaw +
                ", pitch=" + pitch +
                '}';
    }

    @Override
    public Location clone() {
        return new Location(this.world, this.x, this.y, this.z, this.yaw, this.pitch);
    }
}
