package net.servzero.network.packet.out.entity;

import net.servzero.network.packet.Packet;
import net.servzero.network.packet.PacketHandler;
import net.servzero.network.packet.serialization.PacketDataSerializer;

import java.io.IOException;

public class OutPacketEntityHeadLook implements Packet<PacketHandler> {
    private final int entityId;
    private final byte yawAngle;

    public OutPacketEntityHeadLook(int entityId, float yaw) {
        this.entityId = entityId;
        this.yawAngle = (byte) ((yaw * 256.0F) / 360.0F);
    }

    @Override
    public void read(PacketDataSerializer serializer) throws IOException {

    }

    @Override
    public void write(PacketDataSerializer serializer) throws IOException {
        serializer.writeVarInt(entityId);
        serializer.writeByte(yawAngle);
    }

    @Override
    public void handle(PacketHandler handler) {

    }
}
