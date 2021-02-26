package net.servzero.network.packet.out.entity;

import net.servzero.helper.AngleHelper;
import net.servzero.network.packet.Packet;
import net.servzero.network.packet.PacketHandler;
import net.servzero.network.packet.serialization.PacketDataSerializer;

import java.io.IOException;

public class OutPacketEntityTeleport implements Packet<PacketHandler> {
    private final int entityId;
    private final double x;
    private final double y;
    private final double z;
    private final byte yawAngle;
    private final byte pitchAngle;
    private final boolean onGround;

    public OutPacketEntityTeleport(int entityId, double x, double y, double z, byte yaw, byte pitch, boolean onGround) {
        this.entityId = entityId;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yawAngle = AngleHelper.getAngleFromRotation(yaw);
        this.pitchAngle = AngleHelper.getAngleFromRotation(pitch);
        this.onGround = onGround;
    }

    @Override
    public void read(PacketDataSerializer serializer) throws IOException {

    }

    @Override
    public void write(PacketDataSerializer serializer) throws IOException {
        serializer.writeVarInt(this.entityId);
        serializer.writeDouble(this.x);
        serializer.writeDouble(this.y);
        serializer.writeDouble(this.z);
        serializer.writeByte(this.yawAngle);
        serializer.writeByte(this.pitchAngle);
        serializer.writeBoolean(this.onGround);
    }

    @Override
    public void handle(PacketHandler handler) {

    }
}
