package net.servzero.network.packet.out.player;

import net.servzero.helper.AngleHelper;
import net.servzero.network.packet.Packet;
import net.servzero.network.packet.PacketHandler;
import net.servzero.network.packet.serialization.PacketDataSerializer;

import java.io.IOException;
import java.util.UUID;

public class OutPacketSpawnPlayer implements Packet<PacketHandler> {
    private final int entityId;
    private final UUID playerUuid;
    private final double x;
    private final double y;
    private final double z;
    private final byte yawAngle; // (byte)((int) yaw * 256.0F / 360.0F)
    private final byte pitchAngle; // (byte)((int) pitch * 256.0F / 360.0F)

    public OutPacketSpawnPlayer(int entityId, UUID playerUuid, double x, double y, double z, float yaw, float pitch) {
        this.entityId = entityId;
        this.playerUuid = playerUuid;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yawAngle = AngleHelper.getAngleFromRotation(yaw);
        this.pitchAngle = AngleHelper.getAngleFromRotation(pitch);
    }

    @Override
    public void read(PacketDataSerializer serializer) throws IOException {

    }

    @Override
    public void write(PacketDataSerializer serializer) throws IOException {
        serializer.writeVarInt(this.entityId);
        serializer.writeUuid(this.playerUuid);
        serializer.writeDouble(this.x);
        serializer.writeDouble(this.y);
        serializer.writeDouble(this.z);
        serializer.writeByte(this.yawAngle);
        serializer.writeByte(this.pitchAngle);
        serializer.writeByte(255);
    }

    @Override
    public void handle(PacketHandler handler) {

    }
}
