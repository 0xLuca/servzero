package net.servzero.network.packet.out.entity;

import net.servzero.network.packet.serialization.PacketDataSerializer;

import java.io.IOException;

public class OutPacketEntityLook extends OutPacketEntity {
    private byte yawAngle;
    private byte pitchAngle;
    private boolean onGround;

    public OutPacketEntityLook(int entityId, float yaw, float pitch, boolean onGround) {
        super(entityId);
        this.yawAngle = (byte) ((yaw * 256.0F) / 360.0F);
        this.pitchAngle = (byte) ((pitch * 256.0F) / 360.0F);
        this.onGround = onGround;
    }

    @Override
    public void write(PacketDataSerializer serializer) throws IOException {
        super.write(serializer);
        serializer.writeByte(yawAngle);
        serializer.writeByte(pitchAngle);
        serializer.writeBoolean(onGround);
    }
}
