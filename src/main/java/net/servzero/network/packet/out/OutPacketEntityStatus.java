package net.servzero.network.packet.out;

import net.servzero.network.packet.Packet;
import net.servzero.network.packet.PacketHandler;
import net.servzero.network.packet.serialization.PacketDataSerializer;

public class OutPacketEntityStatus implements Packet<PacketHandler> {
    private final int entityId;
    private final byte status;

    public OutPacketEntityStatus(int entityId, byte status) {
        this.entityId = entityId;
        this.status = status;
    }

    @Override
    public void read(PacketDataSerializer serializer) {

    }

    @Override
    public void write(PacketDataSerializer serializer) {
        serializer.writeInt(entityId);
        serializer.writeByte(status);
    }

    @Override
    public void handle(PacketHandler handler) {

    }
}
