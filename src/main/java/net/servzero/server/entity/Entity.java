package net.servzero.server.entity;

import net.servzero.logger.Logger;
import net.servzero.network.packet.Packet;
import net.servzero.network.packet.out.entity.*;
import net.servzero.network.packet.out.player.OutPacketSpawnPlayer;
import net.servzero.server.Server;
import net.servzero.server.player.Player;
import net.servzero.server.world.Location;
import net.servzero.server.world.World;

// TODO: Add world changes
public class Entity {
    private static volatile int entityIdCounter = 0;

    private static synchronized int generateNewEntityId() {
        return entityIdCounter++;
    }

    private static final int ENTITY_RENDER_DISTANCE = 16;

    private final int id;
    private Location lastLocation;
    private Location location;

    protected Entity() {
        this.id = generateNewEntityId();
    }

    public int getId() {
        return id;
    }

    public Location getLastLocation() {
        return lastLocation;
    }

    public Location getLocation() {
        return location;
    }

    public World getWorld() {
        return location.getWorld();
    }

    public boolean isOnGround() {
        return true;
    }

    public void setInitialLocation(Location location) {
        if (this.location == null) {
            this.location = location;
        }
        sendTeleport();
    }

    public void teleport(Location location) {
        setPositionAndRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
    }

    public double distanceToSquared(Entity player) {
        return this.location.distanceToSquared(player.getLocation());
    }

    public double distanceTo(Entity player) {
        return this.location.distanceTo(player.getLocation());
    }

    public boolean isNewInRenderDistanceOf(Entity player) {
        return distanceTo(player) <= Entity.ENTITY_RENDER_DISTANCE
                && this.location.distanceTo(player.getLastLocation()) > Entity.ENTITY_RENDER_DISTANCE;
    }

    public boolean isNewNotInRenderDistanceOf(Entity player) {
        return distanceTo(player) > Entity.ENTITY_RENDER_DISTANCE
                && this.location.distanceTo(player.getLastLocation()) <= Entity.ENTITY_RENDER_DISTANCE;
    }

    public boolean isInRenderDistanceOf(Entity player) {
        return distanceTo(player) <= Entity.ENTITY_RENDER_DISTANCE;
    }

    private void updateVisibleEntities() {
        if (!(this instanceof Player)) {
            return;
        }
        Player thisPlayer = (Player) this;
        Server.getInstance().getWorld().getEntityList().forEach(otherEntity -> {
            if (otherEntity.isNewInRenderDistanceOf(this) || this.isNewInRenderDistanceOf(otherEntity)) {
                if (otherEntity instanceof Player) {
                    Player otherPlayer = (Player) otherEntity;
                    sendPlayerSpawn(thisPlayer, otherPlayer);
                    sendPlayerSpawn(otherPlayer, thisPlayer);
                } else {
                    // TODO: Add entity spawning and sending
                }
            } else if (otherEntity.isNewNotInRenderDistanceOf(this) || this.isNewNotInRenderDistanceOf(otherEntity)) {
                sendDespawn(thisPlayer, otherEntity);
                if (otherEntity instanceof Player) {
                    Player otherPlayer = (Player) otherEntity;
                    sendDespawn(otherPlayer, thisPlayer);
                }
            }
        });
    }

    private void sendPlayerSpawn(Player toSend, Player toSpawn) {
        toSend.getNetworkManager().sendPacket(new OutPacketSpawnPlayer(
                toSpawn.getId(),
                toSpawn.getUniqueId(),
                toSpawn.getLocation().getX(),
                toSpawn.getLocation().getY(),
                toSpawn.getLocation().getZ(),
                toSpawn.getLocation().getYaw(),
                toSpawn.getLocation().getPitch()
        ));
        toSend.getNetworkManager().sendPacket(new OutPacketEntityHeadLook(
                toSpawn.getId(),
                toSpawn.getLocation().getYaw()
        ));
    }

    private void sendDespawn(Player toSend, Entity toDespawn) {
        toSend.getNetworkManager().sendPacket(new OutPacketDestroyEntities(toDespawn.getId()));
    }

    private boolean hasPositionChanged(double newX, double newY, double newZ) {
        final double currentX = this.location.getX();
        final double currentY = this.location.getY();
        final double currentZ = this.location.getZ();
        return newX != currentX || newY != currentY || newZ != currentZ;
    }

    private boolean hasRotationChanged(float newYaw, float newPitch) {
        final float currentYaw = this.location.getYaw();
        final float currentPitch = this.location.getPitch();
        return newYaw != currentYaw || newPitch != currentPitch;
    }

    private void saveLastLocation() {
        this.lastLocation = this.location.clone();
    }

    private void setPositionWithoutSending(double x, double y, double z) {
        this.location.setX(x);
        this.location.setY(y);
        this.location.setZ(z);
        updateVisibleEntities();
    }

    private void setRotationWithoutSending(float yaw, float pitch) {
        this.location.setYaw(yaw);
        this.location.setPitch(pitch);
    }

    public void setPosition(double x, double y, double z) {
        setPositionAndRotation(x, y, z, this.location.getYaw(), this.location.getPitch());
    }

    public void setRotation(float yaw, float pitch) {
        setPositionAndRotation(this.location.getX(), this.location.getY(), this.location.getZ(), yaw, pitch);
    }

    public void setPositionAndRotation(double x, double y, double z, float yaw, float pitch) {
        final boolean posChanged = hasPositionChanged(x, y, z);
        final boolean rotChanged = hasRotationChanged(yaw, pitch);

        if (!posChanged && !rotChanged) {
            return;
        }

        saveLastLocation();

        if (posChanged && rotChanged) {
            setPositionWithoutSending(x, y, z);
            setRotationWithoutSending(yaw, pitch);
            sendPositionAndRotation();
        } else if (posChanged) {
            setPositionWithoutSending(x, y, z);
            sendPosition();
        } else if (rotChanged) {
            setRotationWithoutSending(yaw, pitch);
            sendRotation();
        }
    }

    private void sendPositionAndRotation() {
        OutPacketEntityRelativeMoveLook packet;
        final DeltaPosition delta = calculateDelta();
        if (shouldTeleport(delta)) {
            sendTeleport();
            return;
        }
        packet = new OutPacketEntityRelativeMoveLook(
                this.id,
                delta.getDeltaX(),
                delta.getDeltaY(),
                delta.getDeltaZ(),
                this.location.getYaw(),
                this.location.getPitch(),
                this.isOnGround()
        );
        sendToAllInRangeExceptThis(packet);
        sendHeadLook();
    }

    private void sendPosition() {
        OutPacketEntityRelativeMove packet;
        final DeltaPosition delta = calculateDelta();
        if (shouldTeleport(delta)) {
            sendTeleport();
            return;
        }
        packet = new OutPacketEntityRelativeMove(
                this.id,
                delta.getDeltaX(),
                delta.getDeltaY(),
                delta.getDeltaZ(),
                this.isOnGround()
        );
        sendToAllInRangeExceptThis(packet);
    }

    private void sendRotation() {
        OutPacketEntityLook packet = new OutPacketEntityLook(
                this.id,
                this.location.getYaw(),
                this.location.getPitch(),
                this.isOnGround()
        );
        sendToAllInRangeExceptThis(packet);
        sendHeadLook();
    }

    private void sendHeadLook() {
        OutPacketEntityHeadLook packet = new OutPacketEntityHeadLook(
                this.id,
                this.location.getYaw()
        );
        sendToAllInRangeExceptThis(packet);
    }

    private boolean shouldTeleport(DeltaPosition position) {
        return position.isOverflow();
    }

    private void sendTeleport() {
        OutPacketEntityTeleport packet = new OutPacketEntityTeleport(
                this.id,
                this.location.getX(),
                this.location.getY(),
                this.location.getZ(),
                this.location.getYaw(),
                this.location.getPitch(),
                this.isOnGround()
        );
        sendToAll(packet);
    }

    private DeltaPosition calculateDelta() {
        final double currentX = this.location.getX();
        final double currentY = this.location.getY();
        final double currentZ = this.location.getZ();
        final double prevX = this.lastLocation.getX();
        final double prevY = this.lastLocation.getY();
        final double prevZ = this.lastLocation.getZ();
        final short deltaX = getDelta(currentX, prevX);
        final short deltaY = getDelta(currentY, prevY);
        final short deltaZ = getDelta(currentZ, prevZ);
        return new DeltaPosition(deltaX, deltaY, deltaZ);
    }

    private short getDelta(double current, double prev) {
        return (short) ((current * 32 - prev * 32) * 128);
    }

    private void sendToAll(Packet<?> packet) {
        Server.getInstance().getPlayerList().forEach(player -> player.getNetworkManager().sendPacket(packet));
    }

    private void sendToAllInRange(Packet<?> packet) {
        Server.getInstance().getPlayerList().stream().filter(this::isInRenderDistanceOf).forEach(player -> player.getNetworkManager().sendPacket(packet));
    }

    private void sendToAllInRangeExceptThis(Packet<?> packet) {
        Player thisPlayer = (this instanceof Player) ? (Player) this : null;
        Server.getInstance().getPlayerListExcept(thisPlayer).stream().filter(this::isInRenderDistanceOf).forEach(player -> player.getNetworkManager().sendPacket(packet));
    }
}