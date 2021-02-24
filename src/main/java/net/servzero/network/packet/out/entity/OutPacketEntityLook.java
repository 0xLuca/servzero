package net.servzero.network.packet.out.entity;

import net.servzero.network.packet.serialization.PacketDataSerializer;

import java.io.IOException;

public class OutPacketEntityLook extends OutPacketEntity {
    private float yaw;
    private float pitch;
    private boolean onGround;

    public OutPacketEntityLook(int entityId, float yaw, float pitch, boolean onGround) {
        super(entityId);
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
    }

    @Override
    public void write(PacketDataSerializer serializer) throws IOException {
        super.write(serializer);
        serializer.writeFloat(yaw);
        serializer.writeFloat(pitch);
        serializer.writeBoolean(onGround);
    }
}
