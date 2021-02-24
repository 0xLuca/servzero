package net.servzero.network.packet.out.entity;

import net.servzero.network.packet.serialization.PacketDataSerializer;

import java.io.IOException;

public class OutPacketEntityRelativeMoveLook extends OutPacketEntityRelativeMove {
    private final byte yawAngle;
    private final byte pitchAngle;

    public OutPacketEntityRelativeMoveLook(int entityId, short deltaX, short deltaY, short deltaZ, float yaw, float pitch, boolean onGround) {
        super(entityId, deltaX, deltaY, deltaZ, onGround);
        this.yawAngle = (byte)((int) yaw * 256.0F / 360.0F);
        this.pitchAngle = (byte)((int) pitch * 256.0F / 360.0F);
    }

    @Override
    public void write(PacketDataSerializer serializer) throws IOException {
        serializer.writeVarInt(this.entityId);
        serializer.writeShort(this.deltaX);
        serializer.writeShort(this.deltaY);
        serializer.writeShort(this.deltaZ);
        serializer.writeByte(this.yawAngle);
        serializer.writeByte(this.pitchAngle);
        serializer.writeBoolean(onGround);
    }
}
