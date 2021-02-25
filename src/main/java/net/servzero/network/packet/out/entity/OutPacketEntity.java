package net.servzero.network.packet.out.entity;

import net.servzero.network.packet.Packet;
import net.servzero.network.packet.PacketHandler;
import net.servzero.network.packet.serialization.PacketDataSerializer;

import java.io.IOException;

public class OutPacketEntity implements Packet<PacketHandler> {
    protected final int entityId;

    public OutPacketEntity(int entityId) {
        this.entityId = entityId;
    }

    @Override
    public void read(PacketDataSerializer serializer) throws IOException {

    }

    @Override
    public void write(PacketDataSerializer serializer) throws IOException {
        serializer.writeVarInt(entityId);
    }

    @Override
    public void handle(PacketHandler handler) {

    }
}
