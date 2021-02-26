package net.servzero.network.packet.out.entity;

import net.servzero.network.packet.Packet;
import net.servzero.network.packet.PacketHandler;
import net.servzero.network.packet.serialization.PacketDataSerializer;

import java.io.IOException;

public class OutPacketEntityMetadata implements Packet<PacketHandler> {
    private int entityId;

    @Override
    public void read(PacketDataSerializer serializer) throws IOException {

    }

    @Override
    public void write(PacketDataSerializer serializer) throws IOException {
        serializer.writeVarInt(this.entityId);

        serializer.writeByte(0xff);
    }

    @Override
    public void handle(PacketHandler handler) {

    }
}
