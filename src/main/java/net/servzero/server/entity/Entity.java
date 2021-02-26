package net.servzero.server.entity;

import net.servzero.logger.Logger;
import net.servzero.network.packet.out.entity.OutPacketEntityHeadLook;
import net.servzero.network.packet.out.entity.OutPacketEntityRelativeMoveLook;
import net.servzero.server.Server;
import net.servzero.server.world.Location;
import net.servzero.server.world.block.Coordinate;

public class Entity {
    private static volatile int entityIdCounter = 0;

    private static synchronized int generateNewEntityId() {
        return entityIdCounter++;
    }

    private final int id;
    private Location lastLocation;
    private Location location;

    protected Entity() {
        this.id = generateNewEntityId();
    }

    public Location getLastLocation() {
        return lastLocation;
    }

    public Location getLocation() {
        return location;
    }

    public void teleport(Location location) {
        updateLocation(() -> this.location = location);
    }

    public int getId() {
        return id;
    }

    public void updateLocation(Runnable runnable) {
        this.lastLocation = this.location == null ? null : this.location.clone();
        runnable.run();
        if (this.lastLocation == null) {
            this.lastLocation = this.location;
        } else {
            if (!this.location.getWorld().isChunkLoaded(Coordinate.get(this.location.getX(), this.location.getY(), this.location.getZ()))) {

                return;
            }
        }
        final double currentX = this.location.getX();
        final double currentY = this.location.getY();
        final double currentZ = this.location.getZ();
        final double prevX = this.lastLocation.getX();
        final double prevY = this.lastLocation.getY();
        final double prevZ = this.lastLocation.getZ();
        short deltaX = getDelta(currentX, prevX);
        short deltaY = getDelta(currentY, prevY);
        short deltaZ = getDelta(currentZ, prevZ);
        Server.getInstance().getPlayerList().stream().filter(player -> !player.equals(this)).forEach(player -> {
            player.networkManager.sendPacket(new OutPacketEntityRelativeMoveLook(
                    this.id,
                    deltaX,
                    deltaY,
                    deltaZ,
                    this.location.getYaw(),
                    this.location.getPitch(),
                    true
            ));
            player.networkManager.sendPacket(new OutPacketEntityHeadLook(this.id, this.location.getYaw()));
        });
    }

    private short getDelta(double current, double prev) {
        return (short) ((current * 32 - prev * 32) * 128);
    }
}
