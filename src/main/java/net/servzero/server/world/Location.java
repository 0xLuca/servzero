package net.servzero.server.world;

import net.servzero.helper.MathHelper;
import net.servzero.server.world.block.Position;

import java.util.Objects;

public class Location {
    private final World world;
    private volatile double x;
    private volatile double y;
    private volatile double z;
    private volatile float yaw;
    private volatile float pitch;

    private Location(World world, double x, double y, double z, float yaw, float pitch) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public static Location fromPosition(World world, Position position) {
        return get(world, position.getX(), position.getY(), position.getZ(), 0, 0);
    }

    public static Location get(World world, double x, double y, double z, float yaw, float pitch) {
        return new Location(world, x, y, z, yaw, pitch);
    }

    public Position asPosition() {
        return Position.get(this.x, this.y, this.z);
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

    public double distanceTo(Location other) {
        return Math.sqrt(distanceToSquared(other));
    }

    public double distanceToSquared(Location other) {
        if (other == null) {
            throw new IllegalArgumentException("Cannot measure distance to a null location");
        } else if (other.getWorld() == null || this.world == null) {
            throw new IllegalArgumentException("Cannot measure distance when world is null");
        } else if (!other.getWorld().equals(this.world)) {
            throw  new IllegalArgumentException("Cannot measure distance between different worlds");
        }

        return MathHelper.square(this.x - other.x) + MathHelper.square(this.y - other.y) + MathHelper.square(this.z - other.z);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        int tempX = (int) x - (x < 0 ? 1 : 0);
        int tempZ = (int) z - (z < 0 ? 1 : 0);
        return (int) location.x == tempX && (int) location.y == (int) y && (int) location.z == tempZ && Objects.equals(world, location.world);
    }

    @Override
    public int hashCode() {
        return Objects.hash(world, x, y, z);
    }
}
