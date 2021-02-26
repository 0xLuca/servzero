package net.servzero.network.packet.out;

import net.servzero.network.packet.Packet;
import net.servzero.network.packet.PacketHandler;
import net.servzero.network.packet.serialization.PacketDataSerializer;

import java.io.IOException;

public class OutPacketAnimation implements Packet<PacketHandler> {
    private final int entityId;
    private final byte type;

    public OutPacketAnimation(int entityId, int type) {
        this.entityId = entityId;
        this.type = (byte) type;
    }

    @Override
    public void read(PacketDataSerializer serializer) throws IOException {

    }

    @Override
    public void write(PacketDataSerializer serializer) throws IOException {
        serializer.writeVarInt(this.entityId);
        serializer.writeByte(type);
    }

    @Override
    public void handle(PacketHandler handler) {

    }
}
